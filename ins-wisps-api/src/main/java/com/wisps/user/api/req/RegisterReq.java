package com.wisps.user.api.req;

import com.wisps.req.BaseReq;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq extends BaseReq {
    @NotBlank(message = "mobile must not empty")
    private String mobile;
    private String inviteCode;
    private String password;
}
