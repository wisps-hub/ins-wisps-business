package com.wisps.auth.provider.consts;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MFAMethodStatus {

    UNKNOW(0,"未知"),

    CLOSE(1, "关闭"),

    OPEN(2, "开启"),

    UN_INIT(3, "未初始化"),

    ;

    private static final Map<Integer, MFAMethodStatus> map = new HashMap<Integer, MFAMethodStatus>();

    static {
        for (MFAMethodStatus enumMFAMethodStatus : MFAMethodStatus.values()) {
            map.put(enumMFAMethodStatus.getCode(), enumMFAMethodStatus);
        }
    }

    public static MFAMethodStatus getByCode(int code) {
        return map.get(code);
    }

    private final Integer code;

    private final String name;

    MFAMethodStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


}
