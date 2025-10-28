package com.wisps.article.provider.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleAttachReq {
    private Integer type;
    private List<String> images;
}
