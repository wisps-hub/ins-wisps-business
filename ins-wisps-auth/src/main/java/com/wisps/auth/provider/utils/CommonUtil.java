package com.wisps.auth.provider.utils;

import com.wisps.auth.provider.exception.AuthErrorCode;
import com.wisps.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Locale;

@Slf4j
public class CommonUtil {
    public static String decodeMobile(String region, String encodeMobile) {
        try {
            String realMobile = CommonEncrypt.d(encodeMobile);
            return realMobile;
        } catch (Exception e) {
            log.warn(MessageFormat.format("illegal mobile pattern: mobile={0}", encodeMobile), e);
            throw new BizException(AuthErrorCode.INVALID_PHONE_NO);
        }
    }

    public static String decodeMail(String mail) {
        try {
            String realMail = CommonEncrypt.d(mail);
            return realMail.toLowerCase(Locale.ROOT);
        } catch (Exception e) {
            log.warn(MessageFormat.format("illegal mail pattern: mail={0}", mail), e);
            throw new BizException(AuthErrorCode.INVALID_MAIL);
        }
    }
}
