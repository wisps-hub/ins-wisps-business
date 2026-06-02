package com.wisps.auth.provider.vo.dto;

import com.wisps.auth.provider.consts.CredentialType;
import com.wisps.auth.provider.consts.LoginScene;
import com.wisps.auth.provider.utils.LoginUtil;
import com.wisps.consts.DeviceType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LoginInfo extends LoginBaseInfo {
    protected CredentialType credentialType;
    protected String region;
    protected String identity;
    protected String verifyCode;
    protected String password;

    public LoginInfo mobileLoginInfo(String region, String mobile, String verifyCode, String deviceName,
                                     DeviceType deviceType, String os, String imDeviceId, String deviceId, String ip,
                                     LoginScene loginScene) {
        this.credentialType = CredentialType.MOBILE;
        this.region = region;
        this.identity = mobile;
        this.verifyCode = verifyCode;
        this.loginScene = loginScene;
        this.fillCommonLoginInfo(deviceName, deviceType, os, imDeviceId, deviceId, ip);
        return this;
    }

    public LoginInfo mailLoginInfo(String mail, String verifyCode, String deviceName, DeviceType deviceType, String os,
                                   String imDeviceId, String deviceId, String ip, LoginScene loginScene) {
        this.credentialType = CredentialType.EMAIL;
        this.identity = mail;
        this.verifyCode = verifyCode;
        this.loginScene = loginScene;
        this.fillCommonLoginInfo(deviceName, deviceType, os, imDeviceId, deviceId, ip);
        return this;
    }

    public void fillCommonLoginInfo(String deviceName, DeviceType deviceType, String os, String imDeviceId, String deviceId, String ip){
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.os = os;
        this.imDeviceId = imDeviceId;
        this.deviceId = deviceId;
        this.ip = ip;
    }

    public String getDbIdentity(){
        if(CredentialType.MOBILE == credentialType){
            return LoginUtil.getMobileDbIdentity(region, identity);
        }
        return identity;
    }
}
