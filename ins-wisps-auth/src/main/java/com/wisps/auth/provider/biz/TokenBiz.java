package com.wisps.auth.provider.biz;

import com.wisps.auth.provider.entity.SsoAccountEntity;
import com.wisps.auth.provider.vo.dto.SsoUserInfo;
import com.wisps.auth.provider.vo.dto.Tokens;
import com.wisps.auth.provider.vo.dto.UserAndTeamInfo;

import java.util.Map;

public interface TokenBiz {

    /**
     * 生成token
     *
     * @param accountId 账号id
     * @param deviceId 设备id
     */
    String getAccountAppToken(String accountId, String deviceId);

    /**
     * 缓存token
     *
     * @param userId 用户id
     * @param token token
     * @param ttl 时效
     * @param deviceId 设备id
     */
    void cacheAppToken(String userId, String token, long ttl, String deviceId);

    Map<String, Tokens> batchGetUserTokensAndInit(SsoAccountEntity ssoAccount, Map<String, SsoUserInfo> ssoUserInfoMap, Map<String, UserAndTeamInfo> userAndTeamInfoMap, String deviceId, String imdeviceId, int deviceType);

    Tokens registUserTokens(String userId, String userName, String avatarUrl, String orgId, String deviceId, String imDeviceId, int deviceType);
}
