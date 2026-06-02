package com.wisps.auth.provider.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenCreateResults {
    private TokenCreateResult appTokenResult;
    private TokenCreateResult refreshTokenResult;
}
