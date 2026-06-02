package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CaptchaGetReq {

    @Schema(description = "登录方式 1:mobile 2:mail")
    private Integer identityType = 1;

    @Schema(description = "区域")
    private String region;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "email")
    private String email;

    @Schema(description = "captchaSiteKey")
    private String captchaSiteKey;

    @Schema(description = "captchaToken")
    private String captchaToken;
}
