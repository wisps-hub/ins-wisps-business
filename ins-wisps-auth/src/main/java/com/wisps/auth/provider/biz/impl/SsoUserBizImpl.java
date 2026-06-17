package com.wisps.auth.provider.biz.impl;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableList;
import com.wisps.auth.provider.assemble.SsoUseAssemble;
import com.wisps.auth.provider.biz.SsoUserBiz;
import com.wisps.auth.provider.config.SsoCacheConfig;
import com.wisps.auth.provider.helper.UserHelper;
import com.wisps.auth.provider.mapping.dao.SsoUserInfoDao;
import com.wisps.auth.provider.utils.CommonUtil;
import com.wisps.auth.provider.vo.dto.SsoUserInfo;
import com.wisps.auth.provider.vo.dto.UserAndTeamInfo;
import com.wisps.cache.client.ICache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SsoUserBizImpl implements SsoUserBiz {

    @Autowired
    private SsoUserInfoDao ssoUserInfoDao;
    @Resource(name = "CustomizeStringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserHelper userHelper;

    @Override
    public List<SsoUserInfo> enableSsoUserInfos(List<String> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return ImmutableList.of();
        }
        List<SsoUserInfo> ssoUserInfoList = ssoUserInfos(userIdList);
        return ssoUserInfoList.stream().filter(CommonUtil::isEnableUser).collect(Collectors.toList());
    }

    @Override
    public List<SsoUserInfo> ssoUserInfos(List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return ImmutableList.of();
        }
        List<String> keys = userIds.stream().map(SsoCacheConfig.SSO_USER_INFO::genKey).collect(Collectors.toList());

        Map<String, String> userMap = Optional.ofNullable(this.multiGet(keys)).orElse(Collections.emptyMap());
        List<SsoUserInfo> cacheList = userMap.values().stream().filter(StringUtils::isNotBlank)
                .map(v -> JSONUtil.toBean(v, SsoUserInfo.class))
                .collect(Collectors.toList());

        // 已命中的 uid
        Set<String> cacheUidSet = cacheList.stream().map(SsoUserInfo::getUid).collect(Collectors.toSet());

        // 缓存不存在的uid
        List<String> missUidList = userIds.stream().filter(uid -> !cacheUidSet.contains(uid)).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(missUidList)) {
            List<SsoUserInfo> dbList = ssoUserInfoDao.listByUids(missUidList)
                    .stream().map(SsoUseAssemble::toSsoUserInfo).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(dbList)) {
                dbList.forEach(u -> setSsoUserCache(u.getUid(), u));
                cacheList.addAll(dbList);
            }
        }
        return cacheList;
    }

    @Override
    public List<UserAndTeamInfo> getUserAndTeamInfosOrderByAccess(List<String> userIds) {
        return userHelper.getUserAndTeamInfos(userIds);
    }

    private Map<String, String> multiGet(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyMap();
        }

        List<String> values = redisTemplate.opsForValue().multiGet(keys);
        if (values == null) {
            return Collections.emptyMap();
        }

        Map<String, String> result = new HashMap<>(keys.size());
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), values.get(i));
        }
        return result;
    }

    private void setSsoUserCache(String userId, SsoUserInfo ssoUserInfo){
        if(ssoUserInfo == null){
            return;
        }
        String key = SsoCacheConfig.SSO_USER_INFO.genKey(userId);
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(ssoUserInfo),
                SsoCacheConfig.SSO_USER_INFO.getTtl(), SsoCacheConfig.SSO_USER_INFO.getTimeUnit());
    }
}