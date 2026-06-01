package com.wisps.auth.provider.controller;

import com.wisps.auth.provider.biz.LoginBiz;
import com.wisps.auth.provider.config.LoginConfig;
import com.wisps.auth.provider.utils.CookieUtil;
import com.wisps.auth.provider.utils.IpUtil;
import com.wisps.consts.DeviceType;
import com.wisps.resp.Result;
import consts.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/v3/mobile/verifycode")
    @Operation(summary = "获取手机验证码")
    public Result<VerifyCodeGetResVo> getMobileVerifyCodeV3(@RequestBody @Validated VerifyCodeGetRequestV2Vo verifyCodeGetRequestVo,
                                                            @RequestHeader(Header.deviceId) String deviceId,
                                                            @RequestHeader(Header.deviceType) Integer deviceType
            , HttpServletResponse response) {
        HttpServletRequest httpServletRequest = SessionUtil.getHttpServletRequest();
        String ip = httpServletRequest != null ? IpUtil.extractClientIp(httpServletRequest) : null;
        log.info("getMobileVerifyCodeV3 request verifyCodeGetRequestVo={}, deviceId={} ", verifyCodeGetRequestVo, deviceId);
        VerifyCodeGetResVo verifyCodeGetRespVo = loginService.getLoginVerifyCode(verifyCodeGetRequestVo.getRegion(), verifyCodeGetRequestVo.getMobile(), ip
                , deviceId, deviceType, verifyCodeGetRequestVo.getCaptchaSiteKey(), verifyCodeGetRequestVo.getCaptchaToken());
//        VerifyCodeGetResVo verifyCodeGetRespVo = loginService.getLoginVerifyCode(verifyCodeGetRequestVo.getRegion(), verifyCodeGetRequestVo.getMobile(), verifyCodeGetRequestVo.getBlackBox(), ip, deviceId, deviceType, true);
        if(DeviceType.WEB.getType() == deviceType) {
            CookieUtil.setCookie(response, loginConfig.getCookieDeviceKey(), deviceId, loginConfig.getCookieTokenDomain(), SsoConstants.COOKIE_PATH, true, (int) SsoConstants.DEFAULT_TOKEN_EXPIRE_SECONDS);
        }
        log.info("getMobileVerifyCodeV3 response verifyCodeGetRespVo={}, deviceId={} ", verifyCodeGetRespVo, deviceId);
        return Result.ok(verifyCodeGetRespVo);
    }

}