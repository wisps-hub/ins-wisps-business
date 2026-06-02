package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetTokenByAuthCodeReq {
    @Schema(description = "授权码")
    @NotBlank(message = "authcode must not be blank")
    private String authCode;
    @Schema(description = "登录方式")
    @NotNull(message = "loginType is required")
    private Integer loginType;
}
