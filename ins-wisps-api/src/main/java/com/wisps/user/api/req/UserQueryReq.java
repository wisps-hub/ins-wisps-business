package com.wisps.user.api.req;

import com.wisps.req.BaseReq;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryReq extends BaseReq {
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
}