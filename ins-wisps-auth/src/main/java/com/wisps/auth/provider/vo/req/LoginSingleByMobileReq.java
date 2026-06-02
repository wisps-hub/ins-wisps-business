package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(name = "单用户登录请求")
public class LoginSingleByMobileReq {
    @Schema(description = "uid")
    @NotBlank(message = "uid must not be blank")
    private String uid;

    @Schema(description = "区域")
    @NotBlank(message = "region must not be blank")
    private String region;

    @Schema(description = "手机号")
    @NotBlank(message = "mobile must not be blank")
    private String mobile;

    @Schema(description = "验证码")
    @NotBlank(message = "verifyCode must not be blank")
    private String verifyCode;

}
