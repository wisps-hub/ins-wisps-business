package com.wisps.auth.provider.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "二维码用户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrcodeUserInfoVo implements Serializable {
    @ApiModelProperty(notes = "userId")
    protected String userId;
    @ApiModelProperty(notes = "扫码的用户名称")
    protected String username;
    @ApiModelProperty(notes = "扫码的用户头像")
    private String avatarUrl;
    @ApiModelProperty(notes = "头像Urls")
    protected AvatarUrlsVo avatarUrlsVo;

    public QrcodeUserInfoVo(String userId){
        this.userId = userId;
    }
}
