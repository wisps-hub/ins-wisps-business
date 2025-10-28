package com.wisps.notice.api.consts;

public class NoticeConst {

    public static final Long EXP_5_MIN = 1000 * 60 * 5L;
    public static final String SMS_NOTICE_TITLE = "验证码";
    private static final String CAPTCHA_KEY_PREFIX = "wisps:captcha:%s";

    public static String captchaKey(String mobile){
        return String.format(CAPTCHA_KEY_PREFIX, mobile);
    }

}