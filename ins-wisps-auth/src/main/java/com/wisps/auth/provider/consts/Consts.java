package com.wisps.auth.provider.consts;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static com.wisps.utils.TokenUtil.TOKEN_PREFIX;

public class Consts {

    private static final String CACHE_KEY_FORMAT = "%s:%s:%s";
    public static final Long EXP_30 = 1000 * 60 * 30L;

    public static String tokenKey(String scene, String uid, String key){
        //token:scene:uid:key
        return TOKEN_PREFIX + String.format(CACHE_KEY_FORMAT, scene, uid, key);
    }

    public static final String CN = "cn";
    public static final String EN = "en";

    public static final String ANONYMOUS_USER_NAME_CN = "访客";
    public static final String ANONYMOUS_USER_NAME_EN = "Guest";
    public static final String ANONYMOUS_UID_SUFFIX = "_anony";
    public static final String RECORD_ANONYMOUS_UID_SUFFIX = "_anony_record";

    public static final String APP_ID_PORTAL = "1";
    public static final String APP_ID_DRIVE = "2";
    public static final String APP_ID_OFFICAL = "3";

    public static final byte V1_TOKEN = 1;

    public static final String ORG_ID_ZERO = "0";

    public static final byte[] TOKEN_TAG_APP_TOKEN = "1".getBytes(StandardCharsets.UTF_8);
    public static final byte[] TOKEN_TAG_REFRESH_TOKEN = "2".getBytes(StandardCharsets.UTF_8);

    /**
     * default accessToken expire minute（15天= 15*24*60分钟 = 21600）
     */
    public static final long DEFAULT_TOKEN_EXPIRE_MINNUTE = 21600;
    /** access_token时间 */
    public static final long DEFAULT_TOKEN_EXPIRE_MILLISECONDS = Duration.ofMinutes(DEFAULT_TOKEN_EXPIRE_MINNUTE).toMillis();
    /** refresh_token时间 */
    public static final long DEFAULT_REFRESH_TOKEN_EXPIRE_MILLISECONDS = 2 * Duration.ofMinutes(DEFAULT_TOKEN_EXPIRE_MINNUTE).toMillis();
    /** 匿名用户过期毫秒数360天 */
    public static final long ANONYMOUS_TOKEN_EXPIRE_MILLISECONDS = Duration.ofMinutes(43200 * 12).toMillis();
    /**
     * token在redis失效时间
     */
    public static final long DEFAULT_TOKEN_EXPIRE_SECONDS = DEFAULT_TOKEN_EXPIRE_MILLISECONDS / 1000;
    public static final String WEB_UN_LOGIN_UID = "0";
    public static final int NEW_TOKEN_DEFAULT_HASH = 0;
}