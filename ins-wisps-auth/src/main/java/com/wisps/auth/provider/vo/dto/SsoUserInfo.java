package com.wisps.auth.provider.vo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SsoUserInfo {
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
}