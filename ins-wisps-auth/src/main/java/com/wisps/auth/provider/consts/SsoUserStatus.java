package com.wisps.auth.provider.consts;

public enum SsoUserStatus {
    UN_INIT(0, "未初始化"),
    ENABLE(1, "正常"),
    DISABLE(5, "不可用"),
    DELETE(6, "删除"),
    LEAVE_ORG(7, "离职"),
    DISSOLVE_ORG(8, "组织解散");

    private int code;
    private String desc;

    private SsoUserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static SsoUserStatus getEnum(int code) {
        for(SsoUserStatus value : EnumUserStatus.values()) {
            if(value.getCode() == code)
                return value;
        }
        return null;
    }

}
