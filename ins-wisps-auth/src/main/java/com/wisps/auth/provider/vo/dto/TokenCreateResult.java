package com.wisps.auth.provider.vo.dto;

import com.wisps.auth.provider.consts.TokenUserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenCreateResult {
    /**
     * token
     */
    private String token;
    /**
     * 结束时间戳
     */
    private Long expire;
    /**
     * token类型
     */
    private TokenUserType tokenUserType;
    /** 有效期时间戳 */
    private Long expireMs;
}
