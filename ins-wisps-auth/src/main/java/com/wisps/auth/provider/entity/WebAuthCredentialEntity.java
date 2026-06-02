package com.wisps.auth.provider.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebAuthCredentialEntity {
    /**
     * 主键
     */
    private Long id;
    /**
     * 账号id
     */
    private String accountId;
    /**
     * beem的设备ID
     */
    private String deviceId;
    /**
     * 凭证id
     */
    private byte[] credentialId;
    /**
     * 公钥
     */
    private byte[] publicKey;
    /**
     * 传输方式
     */
    private List<String> transports;
    /**
     * 防重放计数
     */
    private Long signCount;
    /**
     *  凭证名称
     */
    private String credentialName;
    /**
     * 设备类型，参照 DeviceType
     */
    private Integer deviceType;
    /**
     * 创建时间
     */
    private Long createtime;
    /**
     * 修改时间
     */
    private Long modifytime;


    public static class Column{
        public static final String id = "id";
        public static final String accountId = "device_id";
        public static final String deviceId = "device_id";
        public static final String credentialId = "credential_id";
        public static final String publicKey = "public_key";
        public static final String transports = "transports";
        public static final String signCount = "sign_count";
        public static final String credentialName = "credential_name";
        public static final String deviceType = "device_type";
        public static final String createtime = "createtime";
        public static final String modifytime = "modifytime";
    }
}
