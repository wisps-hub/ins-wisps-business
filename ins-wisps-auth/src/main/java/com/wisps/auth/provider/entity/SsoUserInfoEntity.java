package com.wisps.auth.provider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@TableName("sso_user_info")
public class SsoUserInfoEntity implements Serializable {

    @TableId(type = IdType.INPUT)
    /**
     * 组织用户id
     */
    private String uid;
    /**
    * 组织id
    */
    private String oid;
    /**
    * token的盐值
    */
    private String secret;
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

    public static class Column{
        public static final String uid = "uid";
        public static final String oid = "oid";
        public static final String secret = "secret";
        public static final String status = "status";
        public static final String createtime = "createtime";
        public static final String modifytime = "modifytime";
    }
}