package com.wisps.auth.provider.biz.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.wisps.auth.provider.assemble.LoginAssemble;
import com.wisps.auth.provider.biz.LoginBiz;
import com.wisps.auth.provider.biz.MfaAccountBiz;
import com.wisps.auth.provider.biz.SsoUserBiz;
import com.wisps.auth.provider.biz.TokenBiz;
import com.wisps.auth.provider.consts.*;
import com.wisps.auth.provider.entity.SsoAccountEntity;
import com.wisps.auth.provider.entity.SsoUserEntity;
import com.wisps.auth.provider.entity.WebAuthCredentialEntity;
import com.wisps.auth.provider.exception.AuthErrorCode;
import com.wisps.auth.provider.helper.NoticeHelper;
import com.wisps.auth.provider.helper.dto.CaptchaGetDto;
import com.wisps.auth.provider.helper.dto.CaptchaStrategy;
import com.wisps.auth.provider.mapping.dao.SsoAccountDao;
import com.wisps.auth.provider.mapping.dao.SsoUserDao;
import com.wisps.auth.provider.mapping.dao.WebAuthCredentialDao;
import com.wisps.auth.provider.utils.CommonUtil;
import com.wisps.auth.provider.utils.FastUUID;
import com.wisps.auth.provider.utils.LoginUtil;
import com.wisps.auth.provider.vo.dto.*;
import com.wisps.auth.provider.vo.req.GetTokenByAuthCodeReq;
import com.wisps.auth.provider.vo.req.LoginTypeReq;
import com.wisps.auth.provider.vo.req.QrcodeLoginReq;
import com.wisps.auth.provider.vo.resp.*;
import com.wisps.consts.DeviceType;
import com.wisps.exception.BizException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoginBizImpl implements LoginBiz {

    @Autowired
    private SsoAccountDao ssoAccountDao;
    @Autowired
    private SsoUserDao ssoUserDao;
    @Autowired
    private WebAuthCredentialDao webAuthCredentialDao;
    @Autowired
    private NoticeHelper noticeHelper;
    @Autowired
    private TokenBiz tokenBiz;
    @Autowired
    private MfaAccountBiz mfaAccountBiz;
    @Autowired
    private SsoUserBiz ssoUserBiz;

    private List<LoginType> DEFAULT_LOGINTYPE = Lists.newArrayList(LoginType.OTP);
    private List<LoginType> PASSKEY_LOGINTYPE = Lists.newArrayList(LoginType.PASSKEY, LoginType.OTP);

    @Override
    public LoginTypeResp getLoginType(LoginTypeReq loginTypeReq, String deviceId, DeviceType deviceType) {
        CredentialType credentialType = CredentialType.getEnum(loginTypeReq.getIdentityType());
        String logInIdentity = "";
        if(CredentialType.MOBILE.getType() == loginTypeReq.getIdentityType()){
            String region = loginTypeReq.getRegion();
            String moblie = loginTypeReq.getMobile();
            Assert.isTrue(StringUtils.isNotBlank(region) || StringUtils.isNotBlank(moblie), AuthErrorCode.INVALID_PHONE_NO.getCode());
            String realMobile = CommonUtil.decodeMobile(region, loginTypeReq.getMobile());
            logInIdentity = LoginUtil.getMobileDbIdentity(region, realMobile);
        } else if (CredentialType.EMAIL.getType() == loginTypeReq.getIdentityType()){
            Assert.isTrue(StringUtils.isNotBlank(loginTypeReq.getEmail()), AuthErrorCode.INVALID_MAIL.getCode());
            logInIdentity = CommonUtil.decodeMail(loginTypeReq.getEmail());
        }
        List<LoginType> loginTypes = getSupportLoginType(logInIdentity, credentialType, deviceId, deviceType);
        return new LoginTypeResp(loginTypes.stream().map(LoginType::getCode).collect(Collectors.toList()));
    }

    public List<LoginType> getSupportLoginType(String logInIdentity, CredentialType credentialType, String deviceId, DeviceType deviceType) {
        SsoAccountEntity ssoAccount = ssoAccountDao.getAccountByIdentity(logInIdentity, credentialType);
        if(ssoAccount == null){
            return DEFAULT_LOGINTYPE;
        }
        List<WebAuthCredentialEntity> credentialList = webAuthCredentialDao.listByAccountId(ssoAccount.getId());
        if(CollectionUtils.isEmpty(credentialList)){
            return DEFAULT_LOGINTYPE;
        }
        if(DeviceType.IOS == deviceType || DeviceType.ANDROID == deviceType){ //mobile:has data
            return PASSKEY_LOGINTYPE;
        }else if(DeviceType.MAC_PC == deviceType || DeviceType.WIN_PC == deviceType){ //pc:has data and device is same
            Optional<WebAuthCredentialEntity> any = credentialList.stream()
                    .filter(credential -> deviceId.equals(credential.getDeviceId())).findAny();
            if(any.isPresent()){
                return PASSKEY_LOGINTYPE;
            }
        }
        return DEFAULT_LOGINTYPE;
    }

    @Override
    public CaptchaGetResp getMobileCaptcha(String region, String realMobile, String ip, String deviceId, DeviceType deviceType, String captchaSiteKey, String captchaToken) {
        CaptchaGetDto captchaGetDto = noticeHelper.genSendSmsCaptcha(CaptchaStrategy.SSO_LOGIN_CODE.getBizKey(),
                region, realMobile, deviceId, ip, deviceType, captchaSiteKey, captchaToken);
        return new CaptchaGetResp(captchaGetDto.getCaptchaExpireTime(), captchaGetDto.getShowVoiceCallDuration());
    }

    @Override
    public void getVoiceCall(String region, String encryptMobile, String ip, String deviceId, DeviceType deviceType, String lang, String captchaSiteKey, String captchaToken) {
        noticeHelper.genSendVoiceCaptcha(region, encryptMobile, lang, ip, deviceId, deviceType, captchaSiteKey, captchaToken);
    }

    @Override
    public LoginResp loginByMobile(MobileLoginDto mobileLoginDto) {
        String identity = LoginUtil.getMobileDbIdentity(mobileLoginDto.getRegion(), mobileLoginDto.getRealMobile());
        SsoAccountEntity ssoAccount = ssoAccountDao.getAccountByIdentity(identity, CredentialType.MOBILE);

        LoginInfo loginInfo = new LoginInfo().mobileLoginInfo(mobileLoginDto.getRegion(), mobileLoginDto.getRealMobile(), mobileLoginDto.getCaptcha(),
                mobileLoginDto.getDeviceName(), mobileLoginDto.getDeviceType(), mobileLoginDto.getOs(), mobileLoginDto.getImDeviceId()
                , mobileLoginDto.getDeviceId(), mobileLoginDto.getIp(), mobileLoginDto.getLoginScene());
        LoginResult loginResult = null;
        if(ssoAccount == null){
            loginResult = this.doRegist(loginInfo);
        }else {
            loginResult = this.doLogin(ssoAccount, loginInfo);
        }
        return LoginAssemble.toLoginResp(loginResult);
    }

    private LoginResult doLogin(SsoAccountEntity ssoAccount, LoginInfo loginInfo) {
        String accountId = ssoAccount.getId();
        List<SsoUserEntity> relationList = ssoUserDao.listByAccountIds(ImmutableList.of(accountId));
        if (CollectionUtils.isEmpty(relationList)) {
            log.info("login with account with no user, accountId={}", accountId);
            String accountAppToken = tokenBiz.getAccountAppToken(accountId, loginInfo.getDeviceId());
            return new LoginResult(accountId, accountAppToken, ImmutableList.of());
        }
        AccountMfaSetting accountMfaSetting = mfaAccountBiz.getAccountMfa(accountId);
        if (!isDisableMFA(accountMfaSetting, loginInfo.getDeviceType())) {
            // MFA 已开启且当前设备需要二次验证，先发 accountToken，待 PIN 验证通过后再颁发用户 token
            log.info("login with disable mfa, accountId={}", accountId);
            return buildLoginResult(tokenBiz.getAccountAppToken(accountId, loginInfo.getDeviceId()), ImmutableList.of(), accountMfaSetting);
        }
        List<String> loginUidList = relationList.stream().map(SsoUserEntity::getUid).distinct().collect(Collectors.toList());
        List<SsoUserInfo> ssoUserInfoList = ssoUserBiz.enableSsoUserInfos(loginUidList);
        return doLoginCommon(ssoAccount, loginInfo, ssoUserInfoList, Consts.LOGIN_TYPE_ALL, StringUtils.EMPTY);
    }

    private LoginResult doLoginCommon(SsoAccountEntity ssoAccount, LoginInfo loginInfo, List<SsoUserInfo> ssoUserInfoList, String loginTypeAll, String empty) {
        //todo hlp
        return null;
    }

    @Override
    public LoginResp loginByPasskey(MobileLoginDto mobileLoginDto, String requestId, String credential) {
        return null;
    }

    @Override
    public LoginResp loginSingleUserByMobile(SingleUserMobileLoginDto mobileLoginDto) {
        return null;
    }

    @Override
    public QrcodeGetResp getLoginQrcode(String deviceId, String imDeviceId, DeviceType deviceType, QrcodeScene qrcodeScene, String appId, LoginScene loginScene, String appVer) {
        return null;
    }

    @Override
    public QrcodeStatusResp getQrcodeStatus(String qrcodeId, String deviceId, String imDeviceId, DeviceType deviceType, String osVer, String deviceName, String ip) {
        return null;
    }

    @Override
    public void scanQrcode(String uid, String qrcodeId, String deviceId) {

    }

    @Override
    public void confirmQrcodeLogin(String uid, String qrcodeId, AuthorizeType authorizeType, List<String> authorizeUids, String privateKey, String deviceId) {

    }

    @Override
    public MailCaptachaGetResp getMailCaptcha(String email, String lang, String ip, String deviceId, DeviceType deviceType, String captchaSiteKey, String captchaToken) {
        return null;
    }

    @Override
    public LoginResp loginByMail(MailLoginDto mailLoginDto) {
        return null;
    }

    @Override
    public LoginResp loginSingleUserByMail(SingleUserMailLoginDto mailLoginDto) {
        return null;
    }

    @Override
    public LoginResp loginByPasskey(MailLoginDto mailLoginDto, String requestId, String credential) {
        return null;
    }

    @Override
    public QrcodeStatusWebLoginMergeResp getQrcodeStatus4Web(QrcodeLoginReq loginReq, String deviceId, String imDeviceId, DeviceType deviceType, String osVer, String deviceName, String ip, String historySessionId, String hisAuthToken, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public LoginResp getTokenByAuthCode(GetTokenByAuthCodeReq getTokenReq, String deviceId, String imDeviceId, DeviceType deviceType, String osVer, String deviceName, String ip, HttpServletResponse response, String scanUserId, String hisAuthorizeToken, String requestId) {
        return null;
    }

    private LoginResult buildLoginResult(String appToken, List<LoginUserInfo> userList, AccountMfaSetting setting) {
        LoginResult result = new LoginResult(appToken, userList);
        if (setting != null) {
            result.setMfaStatus(setting.getStatus());
            result.setSsoMFAList(setting.getSsoMFAList());
        }
        return result;
    }

    /** MFA 关闭 或 来自非 ios / android 设备，直接登录 */
    private Boolean isDisableMFA(AccountMfaSetting account2FASetting, DeviceType deviceType) {
        if (account2FASetting == null || MFAMethodStatus.CLOSE.equals(account2FASetting.getStatus())) {
            return true;
        }
        return deviceType != DeviceType.IOS && deviceType != DeviceType.ANDROID;
    }

    /**
     * 注册新 SSO 账号（仅创建账号，不创建用户/组织关系）。
     * 账号是登录凭证（手机号/邮箱）的载体，与具体用户的绑定在加入组织时完成。
     */
    private LoginResult doRegist(LoginInfo loginInfo){
        String dbIdentity = loginInfo.getDbIdentity();
        String accountId = FastUUID.next();
        try {
            boolean result = ssoAccountDao.insertAccount(accountId, dbIdentity, loginInfo.getCredentialType());
        } catch (Exception e) {
            log.error(MessageFormat.format("register user error region={0}, identity={1}, credentialType={2}," , loginInfo.getRegion(),  loginInfo.getIdentity(), loginInfo.getCredentialType()), e);
            throw new BizException(AuthErrorCode.SYSTEM_ERR);
        }
        String accountAppToken = tokenBiz.getAccountAppToken(accountId, loginInfo.getDeviceId());
        return new LoginResult(accountId, accountAppToken, ImmutableList.of());
    }

}