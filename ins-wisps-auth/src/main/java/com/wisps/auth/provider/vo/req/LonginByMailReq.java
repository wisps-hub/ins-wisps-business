package com.wisps.auth.provider.vo.req;

import com.wisps.auth.provider.consts.LoginScene;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LonginByMailReq {

    @Schema(description = "邮箱")
    @NotBlank(message = "mail must not be blank")
    private String email;

    @Schema(description = "验证码")
    @NotBlank(message = "captcha must not be blank")
    private String captcha;

    @Schema(description = "登录场景")
    private Integer loginScene = LoginScene.NON_DEVICE_FIRST_LOGIN.getCode();
}
