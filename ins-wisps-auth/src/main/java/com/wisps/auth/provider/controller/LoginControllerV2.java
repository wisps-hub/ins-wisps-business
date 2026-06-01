package com.wisps.auth.provider.controller;

import com.wisps.auth.provider.biz.LoginBiz;
import com.wisps.auth.provider.config.LoginConfig;
import com.wisps.auth.provider.consts.QrcodeStatus;
import com.wisps.auth.provider.utils.CookieUtil;
import com.wisps.auth.provider.utils.HeaderDecoder;
import com.wisps.auth.provider.utils.IpUtil;
import com.wisps.auth.provider.vo.req.CaptchaGetReqV2;
import com.wisps.auth.provider.vo.req.QrcodeLoginReq;
import com.wisps.auth.provider.vo.resp.CaptchaGetResp;
import com.wisps.auth.provider.vo.resp.QrcodeStatusResp;
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
    public Result<CaptchaGetResp> getMobileCaptchaV2(@RequestBody @Validated CaptchaGetReqV2 captchaGetReq) {
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
    public Result<QrcodeStatusResp> getQrcodeStatus4WebV2(@RequestBody @Validated QrcodeLoginReq loginReq) {
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





        QrcodeStatusWebLoginMergeResVo mergeResVo = loginBiz.getQrcodeStatusResult4WebV2(loginReq, deviceId, imDeviceId, deviceType, os, deviceName, ip, null, null, response);
        QrcodeStatusWebLoginResVo qrcodeStatusResVo = mergeResVo.getQrcodeStatusWebLoginResVo();

        if(QrcodeStatus.AUTHORIZED.getCode() == qrcodeStatusResVo.getStatus()){
            GetTokenByAuthCodeRequestVo authCodeGetRequestVo = new GetTokenByAuthCodeRequestVo();
            authCodeGetRequestVo.setAuthCode(mergeResVo.getAuthCode());
            authCodeGetRequestVo.setLoginType(EnumLoginType.QR_CODE.getCode());
            String scanUserId = qrcodeStatusResVo.getShowInfo().getUserId();
            LoginResultResVo loginResult = loginService.getTokenByAuthCode(authCodeGetRequestVo, deviceId, imDeviceId, deviceType, os, deviceName, ip, response, scanUserId, null, null);
            qrcodeStatusResVo.setUserList(loginResult.getUserList());
            qrcodeStatusResVo.setLoginContextId(mergeResVo.getLoginContextId());
        }
        return Result.success(qrcodeStatusResVo);
    }

    @PostMapping("/v2/mail/verifycode")
    @Operation(summary = "获取邮箱验证码")
    public Result<MailVerifyCodeGetResVo> getMailVerifyCodeV2(@RequestBody @Validated MailVerifyCodeRequestV2Vo mailVerifyCodeRequestVo,
                                                              @RequestHeader(Header.deviceId) String deviceId,
                                                              @RequestHeader(Header.deviceType) Integer deviceType,
                                                              @RequestHeader(Header.lang) String lang
            , HttpServletResponse response) {
        HttpServletRequest httpServletRequest = SessionUtil.getHttpServletRequest();
        String ip = httpServletRequest != null ? IpUtil.extractClientIp(httpServletRequest) : null;
        log.info("getMailVerifyCodeV2 request mailVerifyCodeRequestVo={}, deviceId={} ", mailVerifyCodeRequestVo, deviceId);
        MailVerifyCodeGetResVo mailLoginVerifyCode = loginService.getMailLoginVerifyCode(mailVerifyCodeRequestVo.getEmail(), lang, ip
                , deviceId, deviceType, mailVerifyCodeRequestVo.getCaptchaSiteKey(), mailVerifyCodeRequestVo.getCaptchaToken());
//        VerifyCodeGetResVo verifyCodeGetRespVo = loginService.getLoginVerifyCode(verifyCodeGetRequestVo.getRegion(), verifyCodeGetRequestVo.getMobile(), verifyCodeGetRequestVo.getBlackBox(), ip, deviceId, deviceType, true);
        if(DeviceType.WEB.getType() == deviceType) {
            CookieUtil.setCookie(response, loginConfig.getCookieDeviceKey(), deviceId, loginConfig.getCookieTokenDomain(), SsoConstants.COOKIE_PATH, true, (int) SsoConstants.DEFAULT_TOKEN_EXPIRE_SECONDS);
        }
        log.info("getMailVerifyCodeV2 response verifyCodeGetRespVo={}, deviceId={} ", mailLoginVerifyCode, deviceId);
        return Result.ok(mailLoginVerifyCode);
    }

}