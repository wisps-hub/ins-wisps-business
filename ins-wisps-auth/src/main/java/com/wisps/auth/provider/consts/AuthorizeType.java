package com.wisps.auth.provider.consts;

public enum AuthorizeType {
    CURRENT_USER(0, "授权当前用户"),
    ALL_USER(1, "授权所有用户"),
    CANCEL(2, "取消授权"),
    ;
    private int code;
    private String desc;

    AuthorizeType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return desc;
    }


    public static AuthorizeType getEnum(int code) {
        for(AuthorizeType value : AuthorizeType.values()) {
            if(value.getCode() == code)
                return value;
        }
        return null;
    }
}
