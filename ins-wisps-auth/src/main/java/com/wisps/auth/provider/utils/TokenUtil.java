package com.wisps.auth.provider.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class TokenUtil {
    private static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * long -> byte[8]
     */
    public static byte[] longToBytes(long value) {
        return ByteBuffer.allocate(Long.BYTES)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(value)
                .array();
    }

    /**
     * byte[] -> long
     */
    public static long bytesToLong(byte[] bytes) {
        if (bytes.length != Long.BYTES) {
            throw new IllegalArgumentException("bytes length must be 8");
        }
        return ByteBuffer.wrap(bytes)
                .order(ByteOrder.BIG_ENDIAN)
                .getLong();
    }

    /**
     * int -> byte[4]
     */
    public static byte[] intToBytes(int value) {
        return ByteBuffer.allocate(Integer.BYTES)
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(value)
                .array();
    }

    /**
     * byte[] -> int
     */
    public static int bytesToInt(byte[] bytes) {
        if (bytes.length != Integer.BYTES) {
            throw new IllegalArgumentException("bytes length must be 4");
        }
        return ByteBuffer.wrap(bytes)
                .order(ByteOrder.BIG_ENDIAN)
                .getInt();
    }

    /**
     * short -> byte[2]
     */
    public static byte[] shortToBytes(short value) {
        return ByteBuffer.allocate(Short.BYTES)
                .order(ByteOrder.BIG_ENDIAN)
                .putShort(value)
                .array();
    }

    /**
     * HmacSHA256
     */
    public static byte[] hmacSha256(byte[] data, String secretKey) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec key = new SecretKeySpec(
                    secretKey.getBytes(StandardCharsets.UTF_8),
                    HMAC_SHA256
            );
            mac.init(key);
            return mac.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("hmacSha256 error", e);
        }
    }
}
