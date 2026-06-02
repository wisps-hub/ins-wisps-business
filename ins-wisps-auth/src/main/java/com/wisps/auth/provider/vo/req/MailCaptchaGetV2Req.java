package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MailCaptchaGetV2Req {

    @Schema(description = "email")
    @NotBlank(message = "email must not be blank")
    private String email;

    @Schema(description = "captchaSiteKey")
    @NotBlank(message = "captchaSiteKey must not be blank")
    private String captchaSiteKey;

    @Schema(description = "captchaToken")
    private String captchaToken;
}
