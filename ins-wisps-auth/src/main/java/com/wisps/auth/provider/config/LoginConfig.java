package com.wisps.auth.provider.config;

import com.google.common.collect.ImmutableList;
import com.wisps.auth.provider.config.bean.RedirectRuleConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "sso.login")
public class LoginConfig {
    /**
     * 登录二维码有限期
     */
    private long qrcodeTimeOut = 60;
    /** 扫码登录成功token对应的cookiekey */
    private String cookieTokenKey;
    /** 扫码登录成功uid对应的cookiekey */
    private String cookieUidKey;
    /** 扫码登录成功deviceId对应的cookiekey */
    private String cookieDeviceKey;
    /** 扫码登录成功imDeviceId对应的cookiekey */
    private String cookieImDeviceKey;
    /** authToken对应的cookiekey */
    private String cookieAuthTokenKey;
    /** cookieOidKey */
    private String cookieOidKey;
    /** session对应的cookiekey */
    private String cookieSessionKey;
    /** 扫码登录成功cookie的域名 */
    private String cookieTokenDomain;
    /** 测试模式 */
    private Boolean testMode = false;
    /** 风控的域名 */
    private String riskServerDomain;
    /** 风控接口-发送短信验证码异常检测 */
    private String riskMethodVerifyPhone;
    /** 风控接口-获取设备指纹 */
    private String riskMethodGetDeviceId;
    /** 短时token用户List */
    private List<String> momentTokenUserIdList = ImmutableList.of();
    /** 短时token-时间 */
    private long momentTokenMs = 180000;
    /** 刷新token-时间 */
    private long momentRefreshTokenMs = 240000;
    /** passkey认证超时时间 */
    private long passkeyAuthTimeOut = 60000 * 30;
    /** 重定向规则配置 */
    private List<RedirectRuleConfig> redirectRuleConfigs;
    /** secrets */
    private Map<Long, String> secrets;

    private Boolean tokenv3 = true;
}