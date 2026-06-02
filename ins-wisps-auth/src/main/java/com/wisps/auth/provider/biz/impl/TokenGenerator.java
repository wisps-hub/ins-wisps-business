package com.wisps.auth.provider.biz.impl;

import com.wisps.auth.provider.config.LoginConfig;
import com.wisps.auth.provider.consts.Consts;
import com.wisps.auth.provider.consts.TokenUserType;
import com.wisps.auth.provider.consts.TokenVersion;
import com.wisps.auth.provider.exception.AuthErrorCode;
import com.wisps.auth.provider.utils.TokenUtil;
import com.wisps.auth.provider.vo.dto.TokenCreateResult;
import com.wisps.auth.provider.vo.dto.TokenCreateResults;
import com.wisps.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class TokenGenerator {
    // 轮询选取签名密钥，实现多密钥负载分散；keyId 写入 token 供解析时反查
    private final AtomicLong keyIssuer = new AtomicLong(0);

    // TLV 字段类型常量
    private interface TokenField {
        byte TAG = 1;       // token 类型标签（app/refresh）
        byte USER_ID = 2;
        byte ORG_ID = 3;
        byte EXPIRE_TIME = 4;
        byte DEVICE_HASH = 5; // deviceId 的 hashCode，校验 token 与设备绑定
        byte KEY_ID = 6;    // 签名密钥索引
    }

    @Autowired
    private LoginConfig loginConfig;

    public TokenCreateResults createAppTokensNoOrgId(String userId,
                                                      String deviceId,
                                                      TokenVersion tokenVersion){
        return createAppTokensNoOrgId(userId, deviceId, tokenVersion,
                Consts.DEFAULT_TOKEN_EXPIRE_MILLISECONDS, Consts.DEFAULT_REFRESH_TOKEN_EXPIRE_MILLISECONDS);
    }

    public TokenCreateResults createAppTokensNoOrgId(String userId,
                                                     String deviceId,
                                                     TokenVersion tokenVersion,
                                                     Long appTokenExpire, Long refreshTokenExpire){
        if(Consts.V1_TOKEN != tokenVersion.getVersion()){
            log.warn("createAppTokensNoOrgId unsupport token version={}", tokenVersion.getVersion());
            throw new BizException(AuthErrorCode.INVALID_PARAMETER);
        }
        TokenCreateResult appTokenResult = null;
        TokenCreateResult refreshTokenResult = null;
        if(TokenVersion.V1_APP_ACCOUNT_TOKEN == tokenVersion){
            appTokenResult = createToken(userId, Consts.ORG_ID_ZERO, deviceId, tokenVersion, appTokenExpire, Consts.TOKEN_TAG_APP_TOKEN);
        }else if(TokenVersion.V1_ANONYMOUS_TOKEN == tokenVersion){
            appTokenResult = createToken(userId, Consts.ORG_ID_ZERO, deviceId, tokenVersion, appTokenExpire, Consts.TOKEN_TAG_APP_TOKEN);
        }else if(TokenVersion.V1_RECORD_ANONYMOUS_TOKEN == tokenVersion){
            appTokenResult = createToken(userId, Consts.ORG_ID_ZERO, deviceId, tokenVersion, appTokenExpire, Consts.TOKEN_TAG_APP_TOKEN);
        }
        return new TokenCreateResults(appTokenResult, refreshTokenResult);
    }

    private TokenCreateResult createToken(
            String userId,
            String orgId,
            String deviceId,
            TokenVersion tokenVersion,
            long expireMilliseconds, byte[] tagValue) {
        try {
            long keyId = keyIssuer.incrementAndGet() % loginConfig.getSecrets().size();
            String secretKey = loginConfig.getSecrets().get(keyId);

            ByteArrayOutputStream bodyOut = new ByteArrayOutputStream();
            DataOutputStream body = new DataOutputStream(bodyOut);
            long endTimestamp = System.currentTimeMillis() + expireMilliseconds;
            // ===== TLV 写入 =====
            writeTLV(body, TokenField.TAG, tagValue);
            writeTLV(body, TokenField.USER_ID, userId.getBytes(StandardCharsets.UTF_8));
            if (orgId != null) {
                writeTLV(body, TokenField.ORG_ID, orgId.getBytes(StandardCharsets.UTF_8));
            }
            writeTLV(body, TokenField.EXPIRE_TIME, TokenUtil.longToBytes(endTimestamp));
            if (tokenVersion.isContainsDeviceId()) {
                writeTLV(body, TokenField.DEVICE_HASH, TokenUtil.intToBytes(makeDeviceIdHash(deviceId)));
            }
            writeTLV(body, TokenField.KEY_ID, TokenUtil.longToBytes(keyId));
            byte[] bodyBytes = bodyOut.toByteArray();
            // ===== 签名（推荐 HmacSHA256）=====
            byte[] signature = TokenUtil.hmacSha256(bodyBytes, secretKey);
            ByteArrayOutputStream finalOut = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(finalOut);

            out.writeByte(tokenVersion.getVersion());
            out.writeByte(tokenVersion.getType());

            out.write(bodyBytes);
            out.write(signature);

            byte[] temp = finalOut.toByteArray();

            int hashCode = getHashcode(temp);
            out.writeInt(hashCode);

            byte[] data = finalOut.toByteArray();

            obfuscate(data);

            return new TokenCreateResult(
                    Base64.getUrlEncoder().encodeToString(data),
                    endTimestamp,
                    TokenUserType.valueOf(tokenVersion.getType().intValue())
                    , expireMilliseconds);

        } catch (Exception e) {
            throw new BizException(AuthErrorCode.CANNOT_RESOLVE_TOKEN);
        }
    }

    public int makeDeviceIdHash(String deviceId) {
        if(StringUtils.isBlank(deviceId)){
            return Consts.NEW_TOKEN_DEFAULT_HASH;
        }
        return deviceId.hashCode();
    }

    private int getHashcode(byte[] data) {
        int h = 0;
        for (byte datum : data) {
            h = 31 * h + datum;
        }
        return h;
    }

    /**
     * 用末尾 4 字节（hashcode）与前面所有字节逐一异或，混淆 token 内容。
     * 该操作是自身逆运算，加密和解密调用同一方法。
     */
    public void obfuscate(byte[] data) {
        for (int i = 0, j = 0, len = data.length - 4; i < len; i++, j = i % 4) {
            data[i] = (byte)(data[i] ^ data[len + j]);
        }
    }

    private void writeTLV(DataOutputStream out, byte type, byte[] value) throws IOException {
        out.writeByte(type);
        out.writeShort(value.length);
        out.write(value);
    }
}
