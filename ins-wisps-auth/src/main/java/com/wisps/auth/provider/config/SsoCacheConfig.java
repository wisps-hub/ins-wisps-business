package com.wisps.auth.provider.config;

import java.util.concurrent.TimeUnit;

public enum SsoCacheConfig implements CacheConfig {
    QR_CODE("a1",60L, TimeUnit.SECONDS),
    TOKEN("b1",30L, TimeUnit.DAYS), //该有效期由外部指定
    USER_INFO("c1",1L, TimeUnit.MINUTES),
    ENTER_USER("d1",90L, TimeUnit.DAYS),
    SSO_USER("e3",30L, TimeUnit.DAYS),
    ANONYMOUS_UID("f1", -1, TimeUnit.DAYS),
    REFRESH_TOKEN("h1",30L, TimeUnit.DAYS), //该有效期由外部指定

    PASSKEY_REG_CHALLENGE("i1", 30, TimeUnit.MINUTES),
    PASSKEY_LOGIN_CHALLENGE("k1", 30, TimeUnit.MINUTES),
    PASSKEY_REG_PASS_VERIFYCODE("l1", 10, TimeUnit.MINUTES),

    REQ_LIMIT_QRCODE("co:li:qr:",10L, TimeUnit.SECONDS),

    //TODO 时间待修改为5分钟
    AUTHCODE_RELATION("m1",5L, TimeUnit.MINUTES),
    DEVICE_ERASE("sso_m1", -1, TimeUnit.SECONDS),


    ANONYMOUS_INFO("n2", 30, TimeUnit.DAYS),
    LOGIN_SESSION("o1",15L, TimeUnit.DAYS),
    AUTO_LOGIN_REQUEST("p1",3L, TimeUnit.MINUTES),
    REGISTER_REQUEST("q1",1L, TimeUnit.DAYS),
    LOGIN_OR_REGISTER_REQUEST("r1",1L, TimeUnit.DAYS),
    SSO_USER_INFO("s1",30L, TimeUnit.DAYS),
    LOGIN_ACCOUNT_TOKEN_DATA("z1",2L, TimeUnit.DAYS),

    USER_ACCOUNT_MODIFY_LOCK("t1",1L, TimeUnit.DAYS),
    USER_ACCOUNT_MODIFY_INFO("t1",1L, TimeUnit.DAYS),

;

    private String prefix;
    private long ttl;
    private TimeUnit timeUnit;

    SsoCacheConfig(String prefix, long ttl, TimeUnit timeUnit) {
        this.prefix = prefix;
        this.ttl = ttl;
        this.timeUnit = timeUnit;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public long getTtl() {
        return this.ttl;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }
}
