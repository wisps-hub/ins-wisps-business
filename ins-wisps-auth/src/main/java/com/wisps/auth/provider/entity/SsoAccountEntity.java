package com.wisps.auth.provider.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.handler.AesEncryptTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sso账号表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sso_account")
public class SsoAccountEntity {
    /**
     * 账号Id
     */
    private String id;
    /**
     * 登录凭证
     */
    @TableField(typeHandler = AesEncryptTypeHandler.class)
    private String loginIdentity;
    /**
     * 凭证类型：1:手机;2:邮箱
     */
    private Integer identityType;
    /**
     * 创建时间
     */
    private Long createtime;
    /**
     * 修改时间
     */
    private Long modifytime;
    /**
     * 账号是否开启两步认证 0-关闭 1-开启
     */
    private Boolean enableMfa;

    public SsoAccountEntity(String id, String loginIdentity, Integer identityType) {
        this.id = id;
        this.loginIdentity = loginIdentity;
        this.identityType = identityType;
    }

    public static class Column{
        public static final String id = "id";
        public static final String loginIdentity = "login_identity";
        public static final String identityType = "identity_type";
        public static final String createtime = "createtime";
        public static final String modifytime = "modifytime";
        public static final String enableMfa = "enable_mfa";
    }
}
