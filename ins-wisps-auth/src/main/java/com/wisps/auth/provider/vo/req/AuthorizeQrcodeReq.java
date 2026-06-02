package com.wisps.auth.provider.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;


@Data
public class AuthorizeQrcodeReq {
    @Schema(description = "二维码id")
    @NotBlank(message = "qrcodeId must not be blank")
    private String qrcodeId;

    @Schema(description = "授权类型")
    @NotNull(message = "authorizeType must not be null")
    private Integer authorizeType;

    @Schema(description = "授权用户")
    private List<String> authorizeUids;

    @Schema(description = "密聊key")
    private String privateKey;
}