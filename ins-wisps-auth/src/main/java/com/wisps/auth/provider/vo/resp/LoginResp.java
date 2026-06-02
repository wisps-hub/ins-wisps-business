package com.wisps.auth.provider.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResp {
    @ApiModelProperty(notes = "账号token")
    private String appToken;
    @ApiModelProperty(notes = "组织用户")
    private List<LoginUserInfoVo> userList;
    @Schema(description = "开启多因子认证标识 1-关闭，2-开启，3-半开启")
    private Integer mfaStatus;
    @Schema(description = "可选两步认证列表")
    private List<SsoMfaVo> ssoMfaVoList;
    @Schema(description = "loginContextId")
    private String loginContextId;
}
