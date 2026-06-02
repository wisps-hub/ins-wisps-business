package com.wisps.auth.provider.consts;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MFAMethod {

    PinCode(1, "Pin Code"),

    ;

    private static final Map<Integer, MFAMethod> map = new HashMap<Integer, MFAMethod>();

    static {
        for (MFAMethod enumMFAMethod : MFAMethod.values()) {
            map.put(enumMFAMethod.getCode(), enumMFAMethod);
        }
    }

    private final Integer code;

    private final String name;


    MFAMethod(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MFAMethod getByCode(Integer code) {
        return map.get(code);
    }
}
