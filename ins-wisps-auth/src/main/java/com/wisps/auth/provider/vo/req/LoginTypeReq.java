package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoginTypeReq {
    @Schema(description = "凭证类型")
    @NotNull(message = "identityType must not be null")
    private Integer identityType;

    @Schema(description = "区域")
    private String region;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;
}