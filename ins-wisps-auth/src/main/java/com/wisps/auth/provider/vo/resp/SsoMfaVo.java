package com.wisps.auth.provider.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SsoMfaVo {

    @Schema(description = "MFA 认证方法值")
    private Integer mfaMethodCode;

    @Schema(description = "MFA 方法名称")
    private String mfaMethodName;

}
