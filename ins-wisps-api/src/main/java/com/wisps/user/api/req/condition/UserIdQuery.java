package com.wisps.user.api.req.condition;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserIdQuery extends UserQuery {
    /**
     * 用户ID
     */
    private Long uid;
}