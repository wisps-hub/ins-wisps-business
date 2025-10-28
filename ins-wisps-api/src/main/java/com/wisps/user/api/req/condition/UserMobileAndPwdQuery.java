package com.wisps.user.api.req.condition;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserMobileAndPwdQuery extends UserQuery{
    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;
}
