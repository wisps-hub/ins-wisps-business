package com.wisps.auth.provider.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tokens {
    private Long appTokenExpire;
    private String appToken;
    private Long refreshTokenExpire;
    private String refreshToken;
    private String imToken;
    private boolean initImToken;
}
