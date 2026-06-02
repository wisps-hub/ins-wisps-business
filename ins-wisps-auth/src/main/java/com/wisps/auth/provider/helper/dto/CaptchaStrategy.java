package com.wisps.auth.provider.helper.dto;

import com.wisps.auth.provider.utils.CommonUtil;
import com.wisps.cache.consts.WispsCacheConst;
import com.wisps.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public enum CaptchaStrategy {
    SSO_LOGIN_CODE("login",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 180L, TimeUnit.SECONDS)
    ),

    SSO_PASSKEY_CODE("passkey",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),

    SSO_PINCODE_CODE("pincode",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),

    TEAM_CREATE_CODE("createTeam",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),

    TEAM_DISSOLVE_CODE("dissolveTeam",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),

    TEAM_INVITE_CODE("inviteJoin",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),

    TEAM_LINKJOIN_CODE("qrCodeJoinOrg",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),

    TEAM_QRCODE_JOIN_CODE("qrCodeJoinOrg",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),

    COMMERCIALIZE_APP_CODE("commercialApplicant",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),

    ACCOUNT_MODIFY_CODE("accountModify",
            new TtlConfig(600L, 60L, 900L, TimeUnit.SECONDS),
            new TtlConfig(180L, 180L, 900L, TimeUnit.SECONDS)),
    ;

    private String bizKey;
    private TtlConfig emailTtlConfig;
    private TtlConfig mobileTtlConfig;

    private static final String CODE = "code";

    private static final String RESEND = "resend";

    private static final String ERROR = "error";

    private static final TtlConfig DEFAULT_EMAIL_TTL =
            new TtlConfig(600L, 180L, 900L, TimeUnit.SECONDS);

    private static final TtlConfig DEFAULT_MOBILE_TTL =
            new TtlConfig(180L, 180L, 180L, TimeUnit.SECONDS);

    @AllArgsConstructor
    @Getter
    public static class TtlConfig {

        /**
         * 验证码有效期
         */
        private final long codeTtl;

        /**
         * 重发间隔
         */
        private final long resendTtl;

        /**
         * 错误次数限制
         */
        private final long errorTtl;

        private final TimeUnit timeUnit;

    }

    private static final Map<String, CaptchaStrategy> BIZ_GROUP_MAP;

    static {
        BIZ_GROUP_MAP = new HashMap<>();
        for (CaptchaStrategy config : values()) {
            BIZ_GROUP_MAP.put(config.bizKey, config);
        }
    }


    public String getBizKey() {
        return this.bizKey;
    }

    public static TtlConfig getMobileTtl(String bizKey) {
        CaptchaStrategy config = BIZ_GROUP_MAP.get(bizKey);
        return config != null ? config.getMobileTtlConfig() : DEFAULT_MOBILE_TTL;
    }

    public static TtlConfig getEmailTtl(String bizKey) {
        CaptchaStrategy config = BIZ_GROUP_MAP.get(bizKey);
        return config != null ? config.getEmailTtlConfig() : DEFAULT_EMAIL_TTL;
    }

    public static String genOtpCodeKey(String bizKey, String... keys) {

        return CommonUtil.genKey(WispsCacheConst.SEPARATOR, bizKey + WispsCacheConst.SEPARATOR + CODE, keys);
    }

    public static String genOtpResendKey(String bizKey, String... keys) {
        return CommonUtil.genKey(WispsCacheConst.SEPARATOR, bizKey + WispsCacheConst.SEPARATOR + RESEND, keys);
    }

    public static String genOtpErrorKey(String bizKey, String... keys) {
        return CommonUtil.genKey(WispsCacheConst.SEPARATOR, bizKey + WispsCacheConst.SEPARATOR + ERROR, keys);
    }

}
