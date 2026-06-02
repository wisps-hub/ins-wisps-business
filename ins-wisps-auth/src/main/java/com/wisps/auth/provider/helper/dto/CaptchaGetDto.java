package com.wisps.auth.provider.helper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaGetDto {
    /**
     * 短信验证码失效时间(单位/秒)
     */
    private Long captchaExpireTime;
    /**
     * 再次发送验证码时间(单位/秒)
     */
    private Long reSendCaptcha;
    /**
     * 多久后展示语音验证码选项（单位：秒）,-1代表无语音验证码
     */
    private Long showVoiceCallDuration;
}
