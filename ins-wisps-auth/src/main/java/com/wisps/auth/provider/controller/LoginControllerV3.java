package com.wisps.auth.provider.controller;

import com.wisps.auth.provider.biz.LoginBiz;
import com.wisps.auth.provider.config.LoginConfig;
import com.wisps.auth.provider.utils.CookieUtil;
import com.wisps.auth.provider.utils.IpUtil;
import com.wisps.auth.provider.vo.req.CaptchaGetV2Req;
import com.wisps.auth.provider.vo.resp.CaptchaGetResp;
import com.wisps.consts.DeviceType;
import com.wisps.resp.Result;
import consts.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "登录相关接口", description = "登录相关接口")
@RestController
@RequestMapping("/v3/sso/login")
public class LoginControllerV3 extends BaseController{

    @Autowired
    private LoginBiz loginBiz;
    @Autowired
    private LoginConfig loginConfig;

    @PostMapping("/mobile/captcha")
    @Operation(summary = "获取手机验证码")
    public Result<CaptchaGetResp> getMobileVerifyCodeV3(@RequestBody @Validated CaptchaGetV2Req captchaGetV2Req) {
        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String ip = IpUtil.extractClientIp(getHttpServletRequest());


        CaptchaGetResp captchaGetResp = loginBiz.getMobileCaptcha(captchaGetV2Req.getRegion(),
                captchaGetV2Req.getMobile(), ip, deviceId, deviceType, captchaGetV2Req.getCaptchaSiteKey(),
                captchaGetV2Req.getCaptchaToken());
        if(DeviceType.WEB == deviceType) {
            CookieUtil.setCookie(getHttpServletResponse(), loginConfig.getCookieDeviceKey(), deviceId,
                    loginConfig.getCookieTokenDomain(), Header.COOKIE_PATH, true,
                    (int) Header.DEFAULT_TOKEN_EXPIRE_SECONDS);
        }
        return Result.success(captchaGetResp);
    }

}