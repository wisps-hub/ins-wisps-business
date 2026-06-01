package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CaptchaGetReqV2 {
    @Schema(description = "区域")
    @NotBlank(message = "region must not be blank")
    private String region;

    @Schema(description = "手机号")
    @NotBlank(message = "mobile must not be blank")
    private String mobile;

    @Schema(description = "captchaSiteKey")
    @NotBlank(message = "captchaToken must not be blank")
    private String captchaSiteKey;

    @Schema(description = "captchaToken")
    private String captchaToken;

}
