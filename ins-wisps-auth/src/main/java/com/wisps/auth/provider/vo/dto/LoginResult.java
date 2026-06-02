package com.wisps.auth.provider.vo.dto;

import com.google.common.collect.ImmutableList;
import com.wisps.auth.provider.consts.MFAMethodStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    /** accountId */
    private String accountId;
    /** appId */
    private String appId;
    /**
     * authorizeToken
     */
    private String authorizeToken;
    /**
     * sso账号token
     */
    private String appToken;
    /**
     * 组织用户
     */
    private List<LoginUserInfo> userList;

    private MFAMethodStatus mfaStatus = MFAMethodStatus.CLOSE;

    private List<SsoMFA> ssoMFAList = ImmutableList.of();

    public LoginResult(String appToken, List<LoginUserInfo> userList) {
        this.appToken = appToken;
        this.userList = userList;
    }

    public LoginResult(String accountId, String appToken, List<LoginUserInfo> userList) {
        this.accountId = accountId;
        this.appToken = appToken;
        this.userList = userList;
    }

    public LoginResult(List<LoginUserInfo> userList) {
        this.userList = userList;
    }
}
