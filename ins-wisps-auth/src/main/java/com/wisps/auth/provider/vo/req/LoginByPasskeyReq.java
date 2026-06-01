package com.wisps.auth.provider.vo.req;

import com.wisps.auth.provider.consts.LoginScene;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginByPasskeyReq {
    @Schema(description = "区域")
    @NotBlank(message = "region must not be blank")
    private String region;

    @Schema(description = "手机号")
    @NotBlank(message = "mobile must not be blank")
    private String mobile;

    @Schema(description = "请求Id")
    @NotBlank(message = "requestId must not be blank")
    private String requestId;

    @Schema(description = "credential")
    @NotBlank(message = "credential must not be blank")
    private String credential;

    @Schema(description = "登录场景")
    private Integer loginScene = LoginScene.NON_DEVICE_FIRST_LOGIN.getCode();

}
