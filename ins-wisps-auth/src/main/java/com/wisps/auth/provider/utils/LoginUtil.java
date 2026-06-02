package com.wisps.auth.provider.utils;


import com.wisps.auth.provider.consts.Consts;
import com.wisps.auth.provider.consts.UserProfileType;
import com.wisps.auth.provider.vo.dto.I18nName;

import java.util.HashMap;
import java.util.Map;

public class LoginUtil {
    private static final String MOBILE_IDENTITY_SPLIT = "_";

    public static String getMobileDbIdentity(String region, String identity){
        return new StringBuffer(region).append(MOBILE_IDENTITY_SPLIT).append(identity).toString();
    }


    public static I18nName getAnonymousUserNameBySeqId(String seqId){
        String enName = new StringBuffer(Consts.ANONYMOUS_USER_NAME_EN).append(" ").append(seqId).toString();
        String cnName = new StringBuffer(Consts.ANONYMOUS_USER_NAME_CN).append(" ").append(seqId).toString();
        Map<String, String> map = new HashMap<>(){{
            put(Consts.EN, enName);
            put(Consts.CN, cnName);
        }};
        return new I18nName(map, Consts.CN);
    }

    public static I18nName getAnonymousUserNameByUid(String anonymousUid){
        int suffixIndex = anonymousUid.indexOf(Consts.ANONYMOUS_UID_SUFFIX);
        String seqId = null;
        if(suffixIndex != -1){
            seqId = anonymousUid.substring(0, suffixIndex);
        }else{
            seqId = anonymousUid;
        }
        return getAnonymousUserNameBySeqId(seqId);
    }

    public static String getAnonymousUserId(String seqId, UserProfileType userProfileType){
        return seqId + userProfileType.getNameSuffix();
    }


    public static boolean supportRegister(String appId){
        return Consts.APP_ID_OFFICAL.equals(appId);
    }


}