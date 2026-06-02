package com.wisps.auth.provider.consts;

public enum LoginType {
    OTP(0, "手机验证码"),
    PASSKEY(1, "Passkey"),
    QR_CODE(2, "Qrcode");

    private int code;
    private String desc;

    private LoginType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static LoginType getEnum(int code) {
        for(LoginType value : LoginType.values()) {
            if(value.getCode() == code)
                return value;
        }
        return null;
    }

}
