package com.wisps.auth.provider.utils;

import java.util.UUID;

public class FastUUID {

    private final static char[] DIGITS64 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_"
            .toCharArray();

    public static String next() {
        UUID u = UUID.randomUUID();
        return toIDString(u.getMostSignificantBits())
                + toIDString(u.getLeastSignificantBits());
    }

    public static String getShortString(UUID u) {
        if (u == null) {
            return next();
        }
        return toIDString(u.getMostSignificantBits())
                + toIDString(u.getLeastSignificantBits());
    }

    private static String toIDString(long l) {
        char[] buf = "00000000000".toCharArray(); // 限定11位长度
        int length = 11;
        long least = 63L; // 0x0000003FL 00111111
        do {
            buf[--length] = DIGITS64[(int) (l & least)]; // l & least取低6位
            l >>>= 6;
        } while (l != 0);
        return new String(buf);
    }

}
