package com.wisps.auth.provider.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "二维码生成结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrcodeGetResp implements Serializable {
    @ApiModelProperty(notes = "二维码id")
    private String qrcodeId;
    @ApiModelProperty(notes = "二维码超时时间")
    private long timeout;
}
