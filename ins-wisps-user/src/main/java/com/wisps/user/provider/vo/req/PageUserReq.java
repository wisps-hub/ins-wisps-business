package com.wisps.user.provider.vo.req;

import com.wisps.req.PageReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUserReq extends PageReq {
    private String name;
    private Integer state;
}
