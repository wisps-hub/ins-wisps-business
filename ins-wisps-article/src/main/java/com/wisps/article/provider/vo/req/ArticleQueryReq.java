package com.wisps.article.provider.vo.req;

import com.wisps.req.PageReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleQueryReq extends PageReq {
    private Integer status;
    private Long channelId;
    private String beginPubDate;
    private String endPubDate;
    private Long uid;
}
