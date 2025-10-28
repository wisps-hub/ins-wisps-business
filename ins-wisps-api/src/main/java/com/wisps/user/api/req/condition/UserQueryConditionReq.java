package com.wisps.user.api.req.condition;

import com.wisps.req.BaseReq;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryConditionReq extends BaseReq {

    private UserQuery userQuery;

    public UserQueryConditionReq(Long uid) {
        UserIdQuery userIdQuery = new UserIdQuery();
        userIdQuery.setUid(uid);
        this.userQuery = userIdQuery;
    }

    public UserQueryConditionReq(String mobile) {
        UserMobileQuery userMobileQuery = new UserMobileQuery();
        userMobileQuery.setMobile(mobile);
        this.userQuery = userMobileQuery;
    }

    public UserQueryConditionReq(String mobile, String password) {
        UserMobileAndPwdQuery userMobileAndPwdQuery = new UserMobileAndPwdQuery();
        userMobileAndPwdQuery.setMobile(mobile);
        userMobileAndPwdQuery.setPassword(password);
        this.userQuery = userMobileAndPwdQuery;
    }
}
