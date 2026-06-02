package com.wisps.auth.provider.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema(description = "二维码状态结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrcodeStatusWebLoginResp implements Serializable {
    @ApiModelProperty(notes = "二维码状态.0：取消扫描，1：二维码加载完成，2：二维码扫码完成，3：二维码已授权")
    private int status;
    @ApiModelProperty(notes = "扫码的用户信息")
    protected QrcodeUserInfoVo showUserInfo;
    @ApiModelProperty(notes = "用户list")
    private List<LoginUserInfoVo> userList;
    @ApiModelProperty(notes = "loginContextId")
    private String loginContextId;

    public void hideInfo4Web(){
        this.showUserInfo = null;
        this.userList = null;
    }
}
