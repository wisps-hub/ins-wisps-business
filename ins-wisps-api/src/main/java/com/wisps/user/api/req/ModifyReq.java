package com.wisps.user.api.req;

import com.wisps.req.BaseReq;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ModifyReq extends BaseReq {
    @NotNull(message = "uid must not null")
    private Long uid;
    private String nickName;
    private String password;
    private String avatarUrl;
    private String mobile;
}
