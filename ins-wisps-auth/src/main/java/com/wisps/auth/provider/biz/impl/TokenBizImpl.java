package com.wisps.auth.provider.biz.impl;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wisps.auth.provider.biz.TokenBiz;
import com.wisps.auth.provider.config.SsoCacheConfig;
import com.wisps.auth.provider.consts.Consts;
import com.wisps.auth.provider.consts.SsoUserStatus;
import com.wisps.auth.provider.consts.TokenVersion;
import com.wisps.auth.provider.entity.SsoAccountEntity;
import com.wisps.auth.provider.vo.dto.*;
import com.wisps.cache.client.ICache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenBizImpl implements TokenBiz {

    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private ICache redisClient;

    @Override
    public String getAccountAppToken(String accountId, String deviceId) {
        TokenCreateResults TokenCreatorResults = tokenGenerator
                .createAppTokensNoOrgId(accountId, deviceId, TokenVersion.V1_APP_ACCOUNT_TOKEN);
        TokenCreateResult tokenCreatorResult = TokenCreatorResults.getAppTokenResult();
        // cache token
        this.cacheAppToken(accountId, tokenCreatorResult.getToken(), Consts.DEFAULT_TOKEN_EXPIRE_MILLISECONDS, deviceId);
        return tokenCreatorResult.getToken();
    }

    /**
     * 以 token 的 MD5 摘要为 key 写入 Redis，value 为空串表示 NORMAL 状态。
     * 校验时若 key 不存在则 token 失效；若 value 非空则表示被踢出/注销等异常状态。
     */
    @Override
    public void cacheAppToken(String userId, String token, long ttl, String deviceId) {
        String key = SsoCacheConfig.TOKEN.genKey(DigestUtils.md5Hex(token));
        log.info("cacheAppToken appToken:{}, userId:{}, ttl:{}, deviceId={}", token, userId, ttl, deviceId);
        redisClient.set(key, ttl, "");
    }

    @Override
    public Map<String, Tokens> batchGetUserTokensAndInit(SsoAccountEntity ssoAccount,
                                                         Map<String, SsoUserInfo> ssoUserInfoMap,
                                                         Map<String, UserAndTeamInfo> userAndTeamInfoMap,
                                                         String deviceId, String imdeviceId, int deviceType) {
        Map<String, Tokens> tokenMap = this.innerBatchGetUserTokens(ssoUserInfoMap,
                userAndTeamInfoMap, deviceId, imdeviceId, deviceType);
        if(MapUtil.isNotEmpty(tokenMap)){
            List<String> initUids = tokenMap.entrySet().stream()
                    .filter(entry -> entry.getValue().isInitImToken())
                    .map(Map.Entry::getKey).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(initUids)){
                ssoUserInfoPort.updateUserStatus(initUids, SsoUserStatus.ENABLE.getCode());
                log.info("batchGetUserTokensAndInit initUids={}", initUids);
                Runnable task = TraceContext.wrap(() -> {
                    for (String initUid : initUids) {
                        SsoUserInfo ssoUserInfo = ssoUserInfoMap.get(initUid);
                        userPort.activeUser(ssoUserInfo.getOid(), initUid);
                        trackPort.recordSignIn(ssoAccount, initUid, ssoUserInfo.getOid());
                    }
                });
                corePoolTaskExecutor.submit(task);
            }
        }
        return tokenMap;
    }

    @Override
    public Tokens registUserTokens(String userId, String userName, String avatarUrl, String orgId, String deviceId, String imDeviceId, int deviceType) {
        String imToken = imPort.registImToken(userId, avatarUrl, userName, imDeviceId, deviceType);
        TokenCreatorResults results = tokenComponent.createAppTokens(userId, orgId, deviceId, TokenVersion.V4_APP_ORGANIZATION_TOKEN);
        TokenCreatorResult tokenCreatorResult = results.getAppTokenResult();
        TokenCreatorResult refreshTokenCreatorResult = results.getRefreshTokenResult();
        //cache token
        this.cacheAppToken(userId, tokenCreatorResult.getToken(), tokenCreatorResult.getExpireMs(), TimeUnit.MILLISECONDS, deviceId);
        this.cacheRefreshToken(userId, refreshTokenCreatorResult.getToken(), refreshTokenCreatorResult.getExpireMs(), TimeUnit.MILLISECONDS, deviceId);
        return new Tokens(tokenCreatorResult.getExpire(), tokenCreatorResult.getToken(), refreshTokenCreatorResult.getExpire(), refreshTokenCreatorResult.getToken(), imToken, true);
    }

    private Map<String, Tokens> innerBatchGetUserTokens(Map<String, SsoUserInfo> ssoUserInfoMap,
                                                        Map<String, UserAndTeamInfo> userAndTeamInfoMap,
                                                        String deviceId, String imdeviceId, int deviceType){
        if(MapUtil.isEmpty(userAndTeamInfoMap)){
            return ImmutableMap.of();
        }
        Set<String> userIdSet = userAndTeamInfoMap.keySet();
        if(userIdSet.size() == 1){
            String userId = userIdSet.iterator().next();
            return ImmutableMap.of(userId, this.getOrRegistTokens(ssoUserInfoMap.get(userId), userAndTeamInfoMap.get(userId), deviceId, imdeviceId, deviceType));
        }
        Collection<SsoUserInfo> ssoUserList = ssoUserInfoMap.values();
        List<SsoUserInfo> unactiveList = Lists.newArrayList();
        List<SsoUserInfo> normalList = Lists.newArrayList();
        for(SsoUserInfo ssoUserRelationAndInfo : ssoUserList){
            if(ssoUserRelationAndInfo.getStatus().equals(EnumUserStatus.UN_INIT.getCode())){
                unactiveList.add(ssoUserRelationAndInfo);
            }else if(ssoUserRelationAndInfo.getStatus().equals(EnumUserStatus.ENABLE.getCode())){
                normalList.add(ssoUserRelationAndInfo);
            }
        }
        // process unactiveList
        Map<String, Tokens> resultMaps = Maps.newHashMap();
        unactiveList.forEach(unActiveUser -> { // 注册接口循环调用
            UserAndTeamInfo userAndTeamInfo = userAndTeamInfoMap.get(unActiveUser.getUid());
            if(userAndTeamInfo == null){
                log.error("batchGetUserTokensAndInit userAndTeamInfo is null, userId={}", unActiveUser.getUid());
                return;
            }
            Tokens tokens = this.registUserTokens(unActiveUser.getUid(), userAndTeamInfo.getName()
                    , userAndTeamInfo.getAvatarUrl(), unActiveUser.getOid(), deviceId, imdeviceId, deviceType);
            resultMaps.put(unActiveUser.getUid(), tokens);
        });
        // process normalList, support batch query imToken
        if(CollectionUtils.isNotEmpty(normalList)){
            List<String> normalUidList = normalList.stream().map(item -> item.getUid()).collect(Collectors.toList());
            Map<String, String> imTokenMap = this.getImDeviceTokenByUserIds(normalUidList, imdeviceId, deviceType);
            normalList.forEach(normalUser -> {
                Tokens tokens = this.getAppTokenOnly(normalUser.getUid(), normalUser.getOid(), deviceId);
                tokens.setImToken(imTokenMap.get(normalUser.getUid()));
                resultMaps.put(normalUser.getUid(), tokens);
            });
        }
        return resultMaps;
    }

    private Tokens getOrRegistTokens(SsoUserInfo ssoUserInfo, UserAndTeamInfo userAndTeamInfo,
                                     String deviceId, String imdeviceId, int deviceType){
        String userId = ssoUserInfo.getUid();
        if(ssoUserInfo.getStatus().equals(SsoUserStatus.UN_INIT.getCode())){
            return this.registUserTokens(userId, userAndTeamInfo.getName(), userAndTeamInfo.getAvatarUrl(), ssoUserInfo.getOid(), deviceId, imdeviceId, deviceType);
        }else{
            return this.getUserTokens(userId, ssoUserInfo.getOid(), deviceId, imdeviceId, deviceType);
        }
    }
}