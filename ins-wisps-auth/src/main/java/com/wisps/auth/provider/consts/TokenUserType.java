package com.wisps.auth.provider.consts;

public enum TokenUserType {
    ACCOUNT(1, "sso账号"),
    USER_ORG(2, "组织用户"),
    USER_ANONYMOUS(3, "匿名用户"),
    RECORD_USER_ANONYMOUS(4, "录制匿名用户"),
    DEVICE(5, "设备用户")
    ;
    private int code;
    private String desc;

    TokenUserType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return desc;
    }

    public static TokenUserType valueOf(int code) {
        for (TokenUserType e : TokenUserType.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("no such code");
    }
}
