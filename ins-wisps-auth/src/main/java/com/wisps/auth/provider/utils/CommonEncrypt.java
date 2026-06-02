package com.wisps.auth.provider.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CommonEncrypt {
    public CommonEncrypt() {
    }

    private static void revertAndOffset(byte[] s, boolean direct) {
        int i = 0;

        int e;
        int offset;
        for(e = s.length - 1; i < e; --e) {
            offset = (direct ? e : i) * 2 + 3 > 130 ? 130 : (direct ? e : i) * 2 + 3;
            offset = direct ? offset : -offset;
            byte temp = s[e];
            s[e] = (byte)(s[i] + offset);
            offset = (direct ? i : e) * 2 + 3 > 130 ? 130 : (direct ? i : e) * 2 + 3;
            offset = direct ? offset : -offset;
            s[i] = (byte)(temp + offset);
            ++i;
        }

        if (i == e) {
            offset = Math.min(i * 2 + 3, 130);
            offset = direct ? offset : -offset;
            s[i] = (byte)(s[i] + offset);
        }

    }

    public static String e(String d) throws UnsupportedEncodingException {
        byte[] s = d.getBytes();
        revertAndOffset(s, true);
        return new String(Base64.getEncoder().encode(s), StandardCharsets.UTF_8);
    }

    public static String d(String e) {
        byte[] s = e.getBytes();
        s = Base64.getDecoder().decode(s);
        revertAndOffset(s, false);
        return new String(s);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String encode = e("18301497739");
        System.out.println(encode);
        System.out.println(d(encode));
    }
}