package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class QrcodeLoginReq {
    @Schema(description = "qrcodeId")
    @NotBlank(message = "qrcodeId must not be blank")
    private String qrcodeId;
}
