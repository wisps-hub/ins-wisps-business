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
public class RealNameAuthReq extends BaseReq {
    @NotNull(message = "uid must not null")
    private Long uid;
    @NotBlank(message = "realName must not empty")
    private String realName;
    @NotBlank(message = "idCard must not empty")
    private String idCard;
}
