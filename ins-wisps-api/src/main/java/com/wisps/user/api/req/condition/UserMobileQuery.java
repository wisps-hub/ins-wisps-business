package com.wisps.user.api.req.condition;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserMobileQuery extends UserQuery{
    /**
     * 用户手机号
     */
    private String mobile;

}
