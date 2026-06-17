package com.wisps.auth.provider.controller;

import com.wisps.auth.provider.biz.LoginBiz;
import com.wisps.auth.provider.config.LoginConfig;
import com.wisps.auth.provider.consts.AuthorizeType;
import com.wisps.auth.provider.consts.LoginScene;
import com.wisps.auth.provider.consts.QrcodeScene;
import com.wisps.auth.provider.utils.CommonUtil;
import com.wisps.auth.provider.utils.CookieUtil;
import com.wisps.auth.provider.utils.HeaderDecoder;
import com.wisps.auth.provider.utils.IpUtil;
import com.wisps.auth.provider.vo.dto.MailLoginDto;
import com.wisps.auth.provider.vo.dto.MobileLoginDto;
import com.wisps.auth.provider.vo.dto.SingleUserMailLoginDto;
import com.wisps.auth.provider.vo.dto.SingleUserMobileLoginDto;
import com.wisps.auth.provider.vo.req.*;
import com.wisps.auth.provider.vo.resp.*;
import com.wisps.consts.DeviceType;
import com.wisps.resp.Result;
import consts.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "登录相关接口", description = "登录相关接口")
@RestController
@RequestMapping("/v1/sso/login")
public class LoginControllerV1 extends BaseController{

    @Autowired
    private LoginBiz loginBiz;
    @Autowired
    private LoginConfig loginConfig;

    @PostMapping("/logintype")
    @Operation(summary = "获取登录方式")
    public Result<LoginTypeResp> getLoginType(@RequestBody @Validated LoginTypeReq loginTypeReq,
                                              @RequestHeader(Header.deviceId) String deviceId,
                                              @RequestHeader(Header.deviceType) Integer deviceType) {
        DeviceType enumDeviceType = DeviceType.fromCode(deviceType);
        return Result.success(loginBiz.getLoginType(loginTypeReq, deviceId, enumDeviceType));
    }

    @Deprecated
    @PutMapping("/mobile/captcha")
    @Operation(summary = "获取手机验证码")
    public Result<CaptchaGetResp> getMobileCaptcha(@RequestBody @Validated CaptchaGetReq captchaGetReq) {
        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String ip = IpUtil.extractClientIp(getHttpServletRequest());
        String realMobile = CommonUtil.decodeMobile(captchaGetReq.getRegion(), captchaGetReq.getMobile());
        CaptchaGetResp captchaGetResp = loginBiz.getMobileCaptcha(captchaGetReq.getRegion(),
                realMobile, ip, deviceId, deviceType, null, null);

        if(DeviceType.WEB == deviceType) {
            HttpServletResponse response = getHttpServletResponse();
            CookieUtil.setCookie(response, loginConfig.getCookieDeviceKey(), deviceId,
                    loginConfig.getCookieTokenDomain(), Header.COOKIE_PATH, true,
                    (int) Header.DEFAULT_TOKEN_EXPIRE_SECONDS);
        }
        return Result.success(captchaGetResp);
    }

    @PutMapping("/mobile/singleuser/captcha")
    @Operation(summary = "获取手机验证码-单用户场景")
    public Result<CaptchaGetResp> getMobileCaptcha4SingleUser(@RequestBody @Validated CaptchaGetSingleUserReq captchaGetSingleUserReq) {
        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String ip = IpUtil.extractClientIp(getHttpServletRequest());

        CaptchaGetResp captchaGetResp = loginBiz.getMobileCaptcha(captchaGetSingleUserReq.getRegion(),
                captchaGetSingleUserReq.getMobile(), ip, deviceId, deviceType,
                captchaGetSingleUserReq.getCaptchaSiteKey(), captchaGetSingleUserReq.getCaptchaToken());
        return Result.success(captchaGetResp);
    }

    @PutMapping("/mobile/voicecall")
    @Operation(summary = "获取语音验证码")
    public Result<Void> getVoiceCall(@RequestBody @Validated VoiceCallReq voiceCallReq) {
        String deviceId = getHeader(Header.deviceId);
        String lang = getHeader(Header.lang);
        DeviceType deviceType = deviceType();
        String ip = IpUtil.extractClientIp(getHttpServletRequest());
        loginBiz.getVoiceCall(voiceCallReq.getRegion(), voiceCallReq.getMobile(), ip, deviceId, deviceType,
                lang, voiceCallReq.getCaptchaSiteKey(), voiceCallReq.getCaptchaToken());
        return Result.success(null);
    }

    @PostMapping("/mobile")
    @Operation(summary = "用户手机号登录-所有组织")
    public Result<LoginResp> loginByMobile(@RequestBody @Validated LoginByMobileReq loginByMobileReq) {

        String deviceId = getHeader(Header.deviceId);
        String imDeviceId = getHeader(Header.imDeviceId);
        String osVer = getHeader(Header.osVer);
        String deviceName = HeaderDecoder.safeDecode(getHeader(Header.phoneModel));
        String appVer = getHeader(Header.appVer);
        DeviceType deviceType = deviceType();
        String ip = IpUtil.extractClientIp(getHttpServletRequest());
        String realMobile = CommonUtil.decodeMobile(loginByMobileReq.getRegion(), loginByMobileReq.getMobile());

        MobileLoginDto mobileLoginDto = MobileLoginDto.builder().region(loginByMobileReq.getRegion())
                .realMobile(realMobile).captcha(loginByMobileReq.getCaptcha()).deviceName(deviceName)
                .deviceType(deviceType).os(osVer).imDeviceId(imDeviceId).deviceId(deviceId).ip(ip)
                .loginScene(LoginScene.getEnum(loginByMobileReq.getLoginScene())).build();
        LoginResp loginResp = loginBiz.loginByMobile(mobileLoginDto);

        //给web种第一个组织用户的cookie
        if(DeviceType.WEB == deviceType && CollectionUtils.isNotEmpty(loginResp.getUserList())){
            LoginUserInfoVo loginUser = loginResp.getUserList().get(0);
            Map<String, String> attributes = new HashMap<>();
            attributes.put(loginConfig.getCookieTokenKey(), loginUser.getAppToken());
            attributes.put(loginConfig.getCookieDeviceKey(), deviceId);
            attributes.put(loginConfig.getCookieImDeviceKey(), imDeviceId);
            webLoginSetCookie(attributes, loginConfig.getCookieTokenDomain(), Header.COOKIE_PATH, deviceType);
        }
        return Result.success(loginResp);
    }

    @PostMapping("/passkey")
    @Operation(summary = "用户手机号登录-passkey")
    public Result<LoginResp> loginByPasskey(@RequestBody @Validated LoginByPasskeyReq loginByPasskeyReq) {
        String deviceId = getHeader(Header.deviceId);
        String imDeviceId = getHeader(Header.imDeviceId);
        String osVer = getHeader(Header.osVer);
        String deviceName = HeaderDecoder.safeDecode(getHeader(Header.phoneModel));
        String appVer = getHeader(Header.appVer);
        DeviceType deviceType = deviceType();
        String ip = IpUtil.extractClientIp(getHttpServletRequest());
        String mobile = CommonUtil.decodeMobile(loginByPasskeyReq.getRegion(), loginByPasskeyReq.getMobile());


        MobileLoginDto mobileLoginDto = MobileLoginDto.builder().region(loginByPasskeyReq.getRegion())
                .realMobile(mobile).deviceName(deviceName).deviceType(deviceType).os(osVer).imDeviceId(imDeviceId)
                .deviceId(deviceId).ip(ip).loginScene(LoginScene.getEnum(loginByPasskeyReq.getLoginScene())).build();
        LoginResp loginResp = loginBiz.loginByPasskey(mobileLoginDto,
                loginByPasskeyReq.getRequestId(), loginByPasskeyReq.getCredential());

        return Result.success(loginResp);
    }

    @PostMapping("/v1/mobile/singleuser")
    @Operation(summary = "用户手机号登录-单个用户", description = "用来进行单用户操作")
    public Result<LoginResp> loginSingleUserByMobile(@RequestBody @Validated LoginSingleByMobileReq loginReq) {
        String deviceId = getHeader(Header.deviceId);
        String imDeviceId = getHeader(Header.imDeviceId);
        String osVer = getHeader(Header.osVer);
        String deviceName = HeaderDecoder.safeDecode(getHeader(Header.phoneModel));
        String appVer = getHeader(Header.appVer);
        DeviceType deviceType = deviceType();
        String ip = IpUtil.extractClientIp(getHttpServletRequest());
        String mobile = CommonUtil.decodeMobile(loginReq.getRegion(), loginReq.getMobile());

        SingleUserMobileLoginDto mobileLoginDto = SingleUserMobileLoginDto.builder().region(loginReq.getRegion())
                .realMobile(mobile).captcha(loginReq.getVerifyCode()).deviceName(deviceName).deviceType(deviceType)
                .os(osVer).imDeviceId(imDeviceId).deviceId(deviceId).ip(ip)
                .loginScene(LoginScene.NON_DEVICE_FIRST_LOGIN).uid(loginReq.getUid()).build();

        LoginResp loginResp = loginBiz.loginSingleUserByMobile(mobileLoginDto);

        return Result.success(loginResp);
    }

    @PostMapping("/qrcode")
    @Operation(summary = "扫码登录获取二维码")
    public Result<QrcodeGetResp> getLoginQrcode(@RequestBody(required = false) QrcodeGetReq qrcodeGetReq) {
        String deviceId = getHeader(Header.deviceId);
        String appVer = getHeader(Header.appVer);
        DeviceType deviceType = deviceType();
        String imDeviceId = getHeader(Header.imDeviceId);


        QrcodeScene qrcodeScene = qrcodeGetReq == null ?
                QrcodeScene.INDEX_LOGIN_DEFAULT : QrcodeScene.getEnum(qrcodeGetReq.getQrcodeScene());
        LoginScene loginScene = qrcodeScene == QrcodeScene.INDEX_LOGIN_DEFAULT ?
                LoginScene.DEVICE_FIRST_LOGIN : LoginScene.NON_DEVICE_FIRST_LOGIN;

        QrcodeGetResp loginResp = loginBiz.getLoginQrcode(deviceId, imDeviceId, deviceType, qrcodeScene, StringUtils.EMPTY, loginScene, appVer);

        return Result.success(loginResp);
    }

    @PostMapping("/qrcode/status/{qrcodeId}")
    @Operation(summary = "获取二维码扫描或授权结果")
    public Result<QrcodeStatusResp> getQrcodeStatus(@NotBlank(message = "qrcodeId must not be blank")
                                                         @PathVariable("qrcodeId") String qrcodeId) {
        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String imDeviceId = getHeader(Header.imDeviceId);
        String osVer = getHeader(Header.osVer);
        String deviceName = HeaderDecoder.safeDecode(getHeader(Header.phoneModel));
        String appVer = getHeader(Header.appVer);
        String ip = IpUtil.extractClientIp(getHttpServletRequest());

        QrcodeStatusResp qrcodeStatusResp = loginBiz.getQrcodeStatus(qrcodeId,
                deviceId, imDeviceId, deviceType, osVer, deviceName, ip);

        return Result.success(qrcodeStatusResp);
    }

    @PostMapping("/qrcode/scan/{qrcodeId}")
    @Operation(summary = "扫二维码结束后调用")
    public Result<Void> scanQrcode(@NotBlank(message = "qrcodeId must not be blank")
                                       @PathVariable("qrcodeId") String qrcodeId) {
        String deviceId = getHeader(Header.deviceId);
        String uid = getHeader(Header.uid);
        loginBiz.scanQrcode(uid, qrcodeId, deviceId);
        return Result.success(null);
    }

    @PostMapping("/qrcode/authorize/{qrcodeId}")
    @Operation(summary = "二维码确认授权")
    public Result<Void> authorizeQrcodeLogin(@RequestBody @Validated  AuthorizeQrcodeReq authorizeQrcodeReq) {
        String deviceId = getHeader(Header.deviceId);
        String uid = getHeader(Header.uid);
        AuthorizeType authorizeType = AuthorizeType.getEnum(authorizeQrcodeReq.getAuthorizeType());
        loginBiz.confirmQrcodeLogin(uid, authorizeQrcodeReq.getQrcodeId(), authorizeType,
                authorizeQrcodeReq.getAuthorizeUids(), authorizeQrcodeReq.getPrivateKey(), deviceId);
        return Result.success(null);
    }


    @Deprecated
    @PostMapping("/mail/captcha")
    @Operation(summary = "获取邮箱验证码")
    public Result<MailCaptachaGetResp> getMailCaptcha(@RequestBody @Validated MailCaptchaGetReq mailCaptchaGetReq) {

        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String lang = getHeader(Header.lang);
        String ip = IpUtil.extractClientIp(getHttpServletRequest());

        MailCaptachaGetResp resp = loginBiz.getMailCaptcha(mailCaptchaGetReq.getEmail(), lang, ip, deviceId,
                deviceType, mailCaptchaGetReq.getCaptchaSiteKey(), mailCaptchaGetReq.getCaptchaToken());

        if(DeviceType.WEB == deviceType) {
            CookieUtil.setCookie(getHttpServletResponse(), loginConfig.getCookieDeviceKey(),
                    deviceId, loginConfig.getCookieTokenDomain(), Header.COOKIE_PATH, true,
                    (int) Header.DEFAULT_TOKEN_EXPIRE_SECONDS);
        }
        return Result.success(resp);
    }

    @PostMapping("/mail")
    @Operation(summary = "用户邮箱登录-所有组织")
    public Result<LoginResp> loginByMail(@RequestBody @Validated LonginByMailReq longinByMailReq) {
        String deviceId = getHeader(Header.deviceId);
        DeviceType deviceType = deviceType();
        String imDeviceId = getHeader(Header.imDeviceId);
        String osVer = getHeader(Header.osVer);
        String deviceName = HeaderDecoder.safeDecode(getHeader(Header.phoneModel));
        String appVer = getHeader(Header.appVer);
        String ip = IpUtil.extractClientIp(getHttpServletRequest());
        String realMail = CommonUtil.decodeMail(longinByMailReq.getEmail());
        LoginScene loginScene = LoginScene.getEnum(longinByMailReq.getLoginScene());

        MailLoginDto mailLoginDto = MailLoginDto.builder().mail(realMail).captcha(longinByMailReq.getCaptcha())
                .deviceName(deviceName).deviceType(deviceType).os(osVer).imDeviceId(imDeviceId)
                .deviceId(deviceId).ip(ip).loginScene(loginScene).build();
        LoginResp loginResp = loginBiz.loginByMail(mailLoginDto);

        //给web种第一个组织用户的cookie
        if(DeviceType.WEB == deviceType && CollectionUtils.isNotEmpty(loginResp.getUserList())){
            LoginUserInfoVo loginUser = loginResp.getUserList().get(0);
            Map<String, String> attributes = new HashMap<>();
            attributes.put(loginConfig.getCookieTokenKey(), loginUser.getAppToken());
            attributes.put(loginConfig.getCookieDeviceKey(), deviceId);
            attributes.put(loginConfig.getCookieImDeviceKey(), imDeviceId);
            webLoginSetCookie(attributes, loginConfig.getCookieTokenDomain(), Header.COOKIE_PATH, deviceType);
        }
        return Result.success(loginResp);
    }

    @PostMapping("/mail/singleuser")
    @Operation(summary = "用户邮箱登录-单个用户", description = "用来进行单用户操作")
    public Result<LoginResp> loginSingleUserByMail(@RequestBody @Validated LoginSingleUserByMailReq loginReq) {
        DeviceType deviceType = deviceType();
        String deviceId = getHeader(Header.deviceId);
        String imDeviceId = getHeader(Header.imDeviceId);
        String osVer = getHeader(Header.osVer);
        String deviceName = HeaderDecoder.safeDecode(getHeader(Header.phoneModel));
        String appVer = getHeader(Header.appVer);
        String ip = IpUtil.extractClientIp(getHttpServletRequest());
        String realMail = CommonUtil.decodeMail(loginReq.getEmail());

        SingleUserMailLoginDto mailLoginDto = SingleUserMailLoginDto.builder().mail(realMail)
                .captcha(loginReq.getCaptcha()).deviceName(deviceName).deviceType(deviceType)
                .os(osVer).imDeviceId(imDeviceId).deviceId(deviceId).ip(ip).uid(loginReq.getUid())
                .loginScene(LoginScene.NON_DEVICE_FIRST_LOGIN).build();
        LoginResp loginResp = loginBiz.loginSingleUserByMail(mailLoginDto);
        return Result.success(loginResp);
    }

    @PostMapping("/mail/passkey")
    @Operation(summary = "用户邮箱登录-passkey")
    public Result<LoginResp> loginMailByPasskey(@RequestBody @Validated LoginMailByPasskeyReq loginReq) {
        DeviceType deviceType = deviceType();
        String deviceId = getHeader(Header.deviceId);
        String imDeviceId = getHeader(Header.imDeviceId);
        String osVer = getHeader(Header.osVer);
        String deviceName = HeaderDecoder.safeDecode(getHeader(Header.phoneModel));
        String ip = IpUtil.extractClientIp(getHttpServletRequest());
        String realMail = CommonUtil.decodeMail(loginReq.getEmail());
        LoginScene loginScene = LoginScene.getEnum(loginReq.getLoginScene());
        MailLoginDto mailLoginDto = MailLoginDto.builder().mail(realMail).deviceName(deviceName)
                .deviceType(deviceType).os(osVer).imDeviceId(imDeviceId).deviceId(deviceId).ip(ip)
                .loginScene(loginScene).build();
        LoginResp resp = loginBiz.loginByPasskey(mailLoginDto, loginReq.getRequestId(), loginReq.getCredential());
        return Result.success(resp);
    }

}