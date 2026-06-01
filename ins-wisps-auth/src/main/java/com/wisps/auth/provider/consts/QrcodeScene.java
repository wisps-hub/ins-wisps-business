package com.wisps.auth.provider.consts;

public enum QrcodeScene {
    INDEX_LOGIN_DEFAULT(0, "默认登录"),
    @Deprecated
    PORTAL_LOGIN(1, "portal登录"),
    INNER_LOGIN(2, "端内扫码登录");


    private int code;
    private String desc;

    private QrcodeScene(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static QrcodeScene getEnum(Integer code) {
        if(code == null){
            return QrcodeScene.INDEX_LOGIN_DEFAULT;
        }
        for(QrcodeScene value : QrcodeScene.values()) {
            if(value.getCode() == code)
                return value;
        }
        return QrcodeScene.INDEX_LOGIN_DEFAULT;
    }
}
