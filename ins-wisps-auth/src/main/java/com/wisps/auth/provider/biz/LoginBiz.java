package com.wisps.auth.provider.biz;

import com.wisps.auth.provider.consts.AuthorizeType;
import com.wisps.auth.provider.consts.LoginScene;
import com.wisps.auth.provider.consts.QrcodeScene;
import com.wisps.auth.provider.vo.dto.MailLoginDto;
import com.wisps.auth.provider.vo.dto.MobileLoginDto;
import com.wisps.auth.provider.vo.dto.SingleUserMailLoginDto;
import com.wisps.auth.provider.vo.dto.SingleUserMobileLoginDto;
import com.wisps.auth.provider.vo.req.GetTokenByAuthCodeReq;
import com.wisps.auth.provider.vo.req.LoginTypeReq;
import com.wisps.auth.provider.vo.req.QrcodeLoginReq;
import com.wisps.auth.provider.vo.resp.*;
import com.wisps.consts.DeviceType;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * 登录业务
 **/
public interface LoginBiz {
    /**
     * 获取登陆方式
     *
     * @param loginTypeReq loginTypeReq
     * @param deviceId 设备id
     * @param deviceType 设备类型
     * @return LoginTypeResp
     */
    LoginTypeResp getLoginType(LoginTypeReq loginTypeReq, String deviceId, DeviceType deviceType);

    /**
     * 获取手机验证码
     *
     * @param region 区域
     * @param realMobile 手机号
     * @param ip ip
     * @param deviceId 设备id
     * @param deviceType 设备类型
     * @param captchaSiteKey
     * @param captchaToken
     */
    CaptchaGetResp getMobileCaptcha(String region, String realMobile, String ip, String deviceId,
                                    DeviceType deviceType, String captchaSiteKey, String captchaToken);

    /**
     * 获取语音验证码
     *
     * @param region 区域
     * @param encryptMobile 加密手机号
     * @param ip ip
     * @param deviceId 设备id
     * @param deviceType 设备类型
     * @param lang 语言
     * @param captchaSiteKey
     * @param captchaToken
     */
    void getVoiceCall(String region, String encryptMobile, String ip, String deviceId,
                      DeviceType deviceType, String lang, String captchaSiteKey, String captchaToken);

    /**
     * 用户手机号登录-所有组织
     *
     * @param mobileLoginDto mobileLoginDto
     */
    LoginResp loginByMobile(MobileLoginDto mobileLoginDto);

    /**
     * 用户手机号登录-passkey
     *
     * @param mobileLoginDto mobileLoginDto
     * @param requestId 请求id
     * @param credential 凭证
     */
    LoginResp loginByPasskey(MobileLoginDto mobileLoginDto, String requestId, String credential);

    /**
     * 用户手机号登录-单个用户
     *
     * @param mobileLoginDto mobileLoginDto
     */
    LoginResp loginSingleUserByMobile(SingleUserMobileLoginDto mobileLoginDto);

    /**
     * 扫码登录获取二维码
     *
     * @param deviceId 设备id
     * @param imDeviceId im设备id
     * @param deviceType 设备类型
     * @param qrcodeScene 二维码场景码
     * @param appId appId
     * @param loginScene 登录场景码
     * @param appVer app版本
     */
    QrcodeGetResp getLoginQrcode(String deviceId, String imDeviceId, DeviceType deviceType, QrcodeScene qrcodeScene, String appId, LoginScene loginScene, String appVer);

    /**
     * 获取二维码扫描或授权结果
     *
     * @param qrcodeId 二维码id
     * @param deviceId 设备id
     * @param imDeviceId im设备id
     * @param deviceType 设备类型
     * @param osVer 操作系统版本
     * @param deviceName 设备名称
     * @param ip ip
     */
    QrcodeStatusResp getQrcodeStatus(String qrcodeId, String deviceId, String imDeviceId, DeviceType deviceType, String osVer, String deviceName, String ip);

    /**
     * 扫二维码结束后调用
     *
     * @param uid 用户id
     * @param qrcodeId 二维码id
     * @param deviceId 设备id
     */
    void scanQrcode(String uid, String qrcodeId, String deviceId);

    /**
     * 二维码确认授权
     *
     * @param uid 用户id
     * @param qrcodeId 二维码id
     * @param authorizeType 授权类型
     * @param authorizeUids 授权用户
     * @param privateKey 密聊key
     * @param deviceId 设备id
     */
    void confirmQrcodeLogin(String uid, String qrcodeId, AuthorizeType authorizeType, List<String> authorizeUids, String privateKey, String deviceId);

    /**
     *
     *
     * @param email 邮箱
     * @param lang 语言
     * @param ip ip
     * @param deviceId 设备id
     * @param deviceType 设备类型
     * @param captchaSiteKey
     * @param captchaToken
     */
    MailCaptachaGetResp getMailCaptcha(String email, String lang, String ip, String deviceId, DeviceType deviceType, String captchaSiteKey, String captchaToken);

    /**
     * 用户邮箱登录-所有组织
     *
     * @param mailLoginDto mailLoginDto
     */
    LoginResp loginByMail(MailLoginDto mailLoginDto);

    /**
     * 用户邮箱登录-单个用户
     *
     * @param mailLoginDto mailLoginDto
     */
    LoginResp loginSingleUserByMail(SingleUserMailLoginDto mailLoginDto);

    /**
     * 用户邮箱登录-passkey
     *
     * @param mailLoginDto mailLoginDto
     * @param requestId 请求id
     * @param credential 凭证
     */
    LoginResp loginByPasskey(MailLoginDto mailLoginDto, String requestId, String credential);

    /**
     * 获取二维码扫描或授权结果
     *
     * @param loginReq 请求参数
     * @param deviceId 设备id
     * @param imDeviceId im设备id
     * @param deviceType 设备类型
     * @param osVer 操作系统版本
     * @param deviceName 设备名称
     * @param ip ip
     * @param historySessionId 历史会话id
     * @param hisAuthToken 历史授权token
     * @param httpServletResponse response
     */
    QrcodeStatusWebLoginMergeResp getQrcodeStatus4Web(QrcodeLoginReq loginReq, String deviceId, String imDeviceId, DeviceType deviceType, String osVer, String deviceName, String ip, String historySessionId, String hisAuthToken, HttpServletResponse httpServletResponse);

    /**
     * getTokenByAuthCode
     *
     * @param getTokenReq 请求参数
     * @param deviceId 设备id
     * @param imDeviceId im设备id
     * @param deviceType 设备类型
     * @param osVer 操作系统版本
     * @param deviceName 设备名称
     * @param ip ip
     * @param response response
     * @param scanUserId scanUserId
     * @param hisAuthorizeToken 历史授权token
     * @param requestId requestId
     */
    LoginResp getTokenByAuthCode(GetTokenByAuthCodeReq getTokenReq, String deviceId, String imDeviceId, DeviceType deviceType, String osVer, String deviceName, String ip, HttpServletResponse response, String scanUserId, String hisAuthorizeToken, String requestId);

}
