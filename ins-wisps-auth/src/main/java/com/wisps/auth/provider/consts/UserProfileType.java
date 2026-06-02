package com.wisps.auth.provider.consts;

import org.apache.commons.lang3.StringUtils;

public enum UserProfileType {
    USER_ORG(2, "组织用户", StringUtils.EMPTY),
    USER_ANONYMOUS(3, "匿名用户-普通", Consts.ANONYMOUS_UID_SUFFIX),
    MEETING_RECORD_USER_ANONYMOUS(4, "匿名用户-Meeting录制", Consts.RECORD_ANONYMOUS_UID_SUFFIX);
    ;
    private int code;
    private String desc;
    private String nameSuffix;

    UserProfileType(int code, String desc, String nameSuffix) {
        this.code = code;
        this.desc = desc;
        this.nameSuffix = nameSuffix;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return desc;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public static UserProfileType valueOf(Integer code) {
        if(code == null){
            throw new IllegalArgumentException("no such code");
        }
        for (UserProfileType se : UserProfileType.values()) {
            if (se.getCode() == code) {
                return se;
            }
        }
        throw new IllegalArgumentException("no such code");
    }


}
