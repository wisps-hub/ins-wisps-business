package com.wisps.user.api.req;

import com.wisps.req.BaseReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ActiveReq extends BaseReq {
    @NotNull(message = "uid must not null")
    private Long uid;
    @NotBlank(message = "blockChainPlatform must not empty")
    private String blockChainPlatform;
    @NotBlank(message = "blockChainUrl must not empty")
    private String blockChainUrl;
}
