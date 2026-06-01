package com.wisps.auth.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * SSO用户
 */
@Data
@Builder
@AllArgsConstructor
@TableName("sso_user")
public class SsoUserEntity {
    /**
     * 主键
     */
    private String id;
    /**
     * 账号id
     */
    private String accountId;
    /**
     * 组织id
     */
    private String oid;
    /**
     * 组织用户id
     */
    private String uid;
    /**
     * 用户状态：0:未初始化;1:正常
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Long createtime;
    /**
     * 修改时间
     */
    private Long modifytime;
}