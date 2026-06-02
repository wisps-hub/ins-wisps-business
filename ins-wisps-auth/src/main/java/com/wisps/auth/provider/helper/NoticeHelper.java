package com.wisps.auth.provider.helper;

import com.wisps.auth.provider.helper.dto.CaptchaGetDto;
import com.wisps.consts.DeviceType;
import com.wisps.notice.api.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NoticeHelper {

    @DubboReference(version = "1.0.0")
    private NoticeService noticeService;

    public Boolean genSendSmsCaptcha(String mobile){
        return noticeService.genSendSmsCaptcha(mobile);
    }

    //发送手机验证码
    public CaptchaGetDto genSendSmsCaptcha(String bizKey, String region, String realMobile, String deviceId, String ip, DeviceType deviceType, String captchaSiteKey, String captchaToken){
        CaptchaGetDto captchaGetDto = new CaptchaGetDto();
        //todo hlp
        // 发送验证码
        // 记录流水
        return captchaGetDto;
    }

    public void genSendVoiceCaptcha(String region, String encryptMobile, String lang, String ip, String deviceId, DeviceType deviceType, String captchaSiteKey, String captchaToken){
        //todo hlp
        // 发送语音验证码
        // 记录流水
    }

}
