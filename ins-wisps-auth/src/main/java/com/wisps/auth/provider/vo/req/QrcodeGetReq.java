package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "生成二维码请求")
public class QrcodeGetReq {
    @Schema(description = "qrcodeScene")
    private Integer qrcodeScene;
}
