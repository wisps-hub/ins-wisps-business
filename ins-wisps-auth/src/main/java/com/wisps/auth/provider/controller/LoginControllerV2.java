package com.wisps.auth.provider.controller;

import com.wisps.auth.provider.biz.LoginBiz;
import com.wisps.auth.provider.config.LoginConfig;
import com.wisps.auth.provider.consts.LoginType;
import com.wisps.auth.provider.consts.QrcodeStatus;
import com.wisps.auth.provider.utils.CookieUtil;
import com.wisps.auth.provider.utils.HeaderDecoder;
import com.wisps.auth.provider.utils.IpUtil;
import com.wisps.auth.provider.vo.req.CaptchaGetV2Req;
import com.wisps.auth.provider.vo.req.GetTokenByAuthCodeReq;
import com.wisps.auth.provider.vo.req.MailCaptchaGetV2Req;
import com.wisps.auth.provider.vo.req.QrcodeLoginReq;
import com.wisps.auth.provider.vo.resp.*;
import com.wisps.consts.DeviceType;
import com.wisps.resp.Result;
import consts.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "登录相关接口", description = "登录相关接口")
@RestController
@RequestMapping("/v2/sso/login")
public class LoginControllerV2 extends BaseController{

    @Autowired
    private LoginBiz loginBiz;
    @Autowired
    private LoginConfig loginConfig;

    @PutMapping("/mobile/captcha")
    @Operation(summary = "获取手机验证码")
    public Result<CaptchaGetResp> getMobileCaptchaV2(@RequestBody @Validated CaptchaGetV2Req captchaGetReq) {
        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String ip = IpUtil.extractClientIp(getHttpServletRequest());

        CaptchaGetResp resp = loginBiz.getMobileCaptcha(captchaGetReq.getRegion(), captchaGetReq.getMobile(),
                ip, deviceId, deviceType, captchaGetReq.getCaptchaSiteKey(), captchaGetReq.getCaptchaToken());

        if(DeviceType.WEB == deviceType) {
            CookieUtil.setCookie(getHttpServletResponse(), loginConfig.getCookieDeviceKey(), deviceId,
                    loginConfig.getCookieTokenDomain(), Header.COOKIE_PATH, true,
                    (int) Header.DEFAULT_TOKEN_EXPIRE_SECONDS);
        }
        return Result.success(resp);
    }

    @PostMapping("/qrcode/status")
    @Operation(summary = "获取二维码扫描或授权结果")
    public Result<QrcodeStatusWebLoginResp> getQrcodeStatus4WebV2(@RequestBody @Validated QrcodeLoginReq loginReq) {
        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String imDeviceId = getHeader(Header.imDeviceId);
        String osVer = getHeader(Header.osVer);
        String deviceName = HeaderDecoder.safeDecode(getHeader(Header.phoneModel));
        String appVer = getHeader(Header.appVer);
        HttpServletRequest request = getHttpServletRequest();
        String ip = IpUtil.extractClientIp(request);
        String hisSessionId = CookieUtil.getCookieValue(request, loginConfig.getCookieSessionKey());
        String hisAuthorizeToken = CookieUtil.getCookieValue(request, loginConfig.getCookieAuthTokenKey());
        HttpServletResponse response = getHttpServletResponse();
        QrcodeStatusWebLoginMergeResp mergeResp = loginBiz.getQrcodeStatus4Web(loginReq, deviceId, imDeviceId,
                deviceType, osVer, deviceName, ip, null, null, response);
        QrcodeStatusWebLoginResp qrcodeStatusWebLoginResp = mergeResp.getQrcodeStatusWebLoginResp();

        if(QrcodeStatus.AUTHORIZED.getCode() == qrcodeStatusWebLoginResp.getStatus()){
            GetTokenByAuthCodeReq  getTokenReq = new GetTokenByAuthCodeReq();
            getTokenReq.setAuthCode(mergeResp.getAuthCode());
            getTokenReq.setLoginType(LoginType.QR_CODE.getCode());
            String scanUserId = qrcodeStatusWebLoginResp.getShowUserInfo().getUserId();
            LoginResp loginResp = loginBiz.getTokenByAuthCode(getTokenReq, deviceId,
                    imDeviceId, deviceType, osVer, deviceName, ip, response, scanUserId, null, null);
            qrcodeStatusWebLoginResp.setUserList(loginResp.getUserList());
            qrcodeStatusWebLoginResp.setLoginContextId(mergeResp.getLoginContextId());
        }
        return Result.success(qrcodeStatusWebLoginResp);
    }

    @PostMapping("/mail/captcha")
    @Operation(summary = "获取邮箱验证码")
    public Result<MailCaptachaGetResp> getMailVerifyCodeV2(@RequestBody @Validated MailCaptchaGetV2Req mailCaptchaGetV2Req) {
        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String lang = getHeader(Header.lang);
        HttpServletRequest request = getHttpServletRequest();
        String ip = IpUtil.extractClientIp(request);


        MailCaptachaGetResp resp = loginBiz.getMailCaptcha(mailCaptchaGetV2Req.getEmail(), lang, ip
                , deviceId, deviceType, mailCaptchaGetV2Req.getCaptchaSiteKey(), mailCaptchaGetV2Req.getCaptchaToken());
        if(DeviceType.WEB == deviceType) {
            CookieUtil.setCookie(getHttpServletResponse(), loginConfig.getCookieDeviceKey(), deviceId,
                    loginConfig.getCookieTokenDomain(), Header.COOKIE_PATH, true,
                    (int) Header.DEFAULT_TOKEN_EXPIRE_SECONDS);
        }
        return Result.success(resp);
    }

}