package com.wisps.auth.provider.consts;

public enum CredentialType {
    MOBILE(1, "手机号"),
    EMAIL(2, "邮箱"),
    ;
    private int type;
    private String desc;

    CredentialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return this.type;
    }

    public String getDesc() {
        return desc;
    }

    public static CredentialType getEnum(Integer code) {
        if (code != null){
            for(CredentialType value : CredentialType.values()) {
                if(value.getType() == code)
                    return value;
            }
        }
        return null;
    }

}
