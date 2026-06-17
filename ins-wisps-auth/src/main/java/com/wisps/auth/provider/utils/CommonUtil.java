package com.wisps.auth.provider.utils;

import com.wisps.auth.provider.consts.SsoUserStatus;
import com.wisps.auth.provider.exception.AuthErrorCode;
import com.wisps.auth.provider.vo.dto.SsoUserInfo;
import com.wisps.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;

@Slf4j
public class CommonUtil {
    public static String encrypt(String data) {
        try {
            return CommonEncrypt.e(data);
        } catch (UnsupportedEncodingException e) {
            log.error("encrypt UnsupportedEncodingException", e);
            return data;
        }
    }

    public static String decodeMobile(String region, String encodeMobile) {
        try {
            return CommonEncrypt.d(encodeMobile);
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

    public static String genKey(String delimiter,String prefix,String... keys){
        if(keys.length==0) {
            throw new BizException(AuthErrorCode.INVALID_PARAMETER);
        }
        StringBuilder sb=new StringBuilder(prefix);
        for(String k:keys){
            sb.append(delimiter).append(k);
        }
        return sb.toString();
    }

    public static boolean isEnableUser(SsoUserInfo ssoUserInfo){
        if (ssoUserInfo == null) {
            return false;
        }
        SsoUserStatus enumUserStatus = SsoUserStatus.getEnum(ssoUserInfo.getStatus());
        return enumUserStatus != null && enumUserStatus.getCode() <= SsoUserStatus.ENABLE.getCode();
    }
}
