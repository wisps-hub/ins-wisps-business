package com.wisps.auth.provider.consts;

public enum QrcodeStatus {
    UN_AUTHORIZED(0, "二维码取消授权"),
    LOADED(1, "二维码加载完成"),
    SCANNED(2, "二维码已扫"),
    AUTHORIZED(3, "二维码已授权"),

    ;
    private int code;
    private String desc;

    QrcodeStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return desc;
    }


    public static QrcodeStatus getEnum(int code) {
        for(QrcodeStatus value : QrcodeStatus.values()) {
            if(value.getCode() == code)
                return value;
        }
        return null;
    }
}
