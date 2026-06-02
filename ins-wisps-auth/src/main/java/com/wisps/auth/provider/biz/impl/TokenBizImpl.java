package com.wisps.auth.provider.biz.impl;

import com.wisps.auth.provider.biz.TokenBiz;
import com.wisps.auth.provider.config.SsoCacheConfig;
import com.wisps.auth.provider.consts.Consts;
import com.wisps.auth.provider.consts.TokenVersion;
import com.wisps.auth.provider.vo.dto.TokenCreateResult;
import com.wisps.auth.provider.vo.dto.TokenCreateResults;
import com.wisps.cache.client.ICache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
}