package com.wisps.article.provider.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleReq {
    private Long id;
    private String title;
    private Long channelId;
    private String content;
    private ArticleAttachReq cover;
}
