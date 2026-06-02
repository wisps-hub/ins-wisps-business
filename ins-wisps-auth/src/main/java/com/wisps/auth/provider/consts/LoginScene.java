package com.wisps.auth.provider.consts;

public enum LoginScene {
    NON_DEVICE_FIRST_LOGIN(0, "非设备首次登录"),
    DEVICE_FIRST_LOGIN(1, "设备首次登录");

    private int code;
    private String desc;

    private LoginScene(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static LoginScene getEnum(int code) {
        for(LoginScene value : LoginScene.values()) {
            if(value.getCode() == code)
                return value;
        }
        return null;
    }

}
