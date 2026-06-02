package com.wisps.auth.provider.vo.dto;

import com.wisps.auth.provider.consts.LoginScene;
import com.wisps.consts.DeviceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class LoginBaseInfo {
    protected DeviceType deviceType;
    protected String deviceName;
    protected String deviceId;
    protected String imDeviceId;
    protected String os;
    protected String ip;
    protected LoginScene loginScene;
    protected String loginApp;
}
