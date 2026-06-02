package com.wisps.auth.provider.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo {
    private Long appTokenExpire;
    private String appToken;
    private Long activeExpire;
    private String refreshToken;
    private String imToken;
    private UserAndTeamInfo userInfo;
}
