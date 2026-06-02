package com.wisps.auth.provider.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "获取短信验证码结果")
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaGetResp {
    @Schema(description = "短信验证码失效时间(单位/秒)")
    private Long captchaExpireTime;

    @Schema(description = "多久后展示语音验证码选项（单位：秒）,-1代表无语音验证码")
    private Long showVoiceCallDuration;

    public CaptchaGetResp(Long captchaExpireTime) {
        this.captchaExpireTime = captchaExpireTime;
    }
}
