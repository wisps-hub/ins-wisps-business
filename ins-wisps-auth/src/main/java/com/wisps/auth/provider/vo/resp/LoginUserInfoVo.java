package com.wisps.auth.provider.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfoVo implements Serializable {
    @ApiModelProperty(notes = "appToken过期时间")
    protected Long appTokenExpire;
    @ApiModelProperty(notes = "appToken")
    protected String appToken;
    @ApiModelProperty(notes = "活跃时间")
    protected Long activeExpire;
    @ApiModelProperty(notes = "refreshToken")
    protected String refreshToken;
    @ApiModelProperty(notes = "imToken")
    protected String imToken;
    @ApiModelProperty(notes = "用户信息 带组织信息")
    protected UserInfoWithTeamVo userInfo;
}
