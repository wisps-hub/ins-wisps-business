package com.wisps.auth.provider.vo.dto;

import com.wisps.auth.provider.consts.LoginScene;
import com.wisps.consts.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MailLoginDto {
    private String mail;
    private String captcha;
    private String deviceName;
    private DeviceType deviceType;
    private String os;
    private String imDeviceId;
    private String deviceId;
    private String ip;
    private String loginApp;
    private LoginScene loginScene;
}
