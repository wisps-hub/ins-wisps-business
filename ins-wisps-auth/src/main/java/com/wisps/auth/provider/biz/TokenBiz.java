package com.wisps.auth.provider.biz;

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
}
