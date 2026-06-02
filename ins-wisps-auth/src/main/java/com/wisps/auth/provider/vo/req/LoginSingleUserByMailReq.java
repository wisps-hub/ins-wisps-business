package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(name = "单用户邮箱登录请求")
public class LoginSingleUserByMailReq {
    @Schema(description = "uid")
    @NotBlank(message = "uid must not be blank")
    private String uid;

    @Schema(description = "mail")
    @NotBlank(message = "mail must not be blank")
    private String email;

    @Schema(description = "验证码")
    @NotBlank(message = "captcha must not be blank")
    private String captcha;

}
