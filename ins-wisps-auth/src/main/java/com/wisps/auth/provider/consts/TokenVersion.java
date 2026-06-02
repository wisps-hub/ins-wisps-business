package com.wisps.auth.provider.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum TokenVersion {
    V1_APP_ACCOUNT_TOKEN(Consts.V1_TOKEN, TokenVersion.TYPE_ACCOUNT_TOKEN,true, "toB账号token"),
    V1_APP_ORGANIZATION_TOKEN(Consts.V1_TOKEN, TokenVersion.TYPE_ORGANIZATION_TOKEN,true, "toB组织Token"),
    V1_ANONYMOUS_TOKEN(Consts.V1_TOKEN, TokenVersion.TYPE_ANONYMOUS_TOKEN,true, "匿名用户token"),
    V1_RECORD_ANONYMOUS_TOKEN(Consts.V1_TOKEN, TokenVersion.TYPE_RECORD_ANONYMOUS_TOKEN,true, "匿名录制用户token"),
    V1_DEVICE_TOKEN(Consts.V1_TOKEN, TokenVersion.TYPE_DEVICE_TOKEN,true, "设备token"),
    ;
    public static final byte TYPE_ACCOUNT_TOKEN = 1;
    public static final byte TYPE_ORGANIZATION_TOKEN = 2;
    public static final byte TYPE_ANONYMOUS_TOKEN= 3;
    public static final byte TYPE_RECORD_ANONYMOUS_TOKEN= 4;
    public static final byte TYPE_DEVICE_TOKEN = 5;

    private Byte version;
    private Byte type;
    private boolean containsDeviceId;
    private String desc;

    public static TokenVersion parse(Byte version, Byte type) {
        for (TokenVersion tmp : TokenVersion.values()) {
            if (Objects.equals(tmp.getVersion(), version)) {
                if(type != null && Objects.equals(tmp.getType(), type)){
                    return tmp;
                }
            }
        }
        return null;
    }
}
