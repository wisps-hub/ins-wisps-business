package com.wisps.article.provider.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleVo implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Long channelId;
    private Integer coverType;
    private String images;
    private Integer status;
    private Date pubTime;
    private Integer readCount;
    private Integer commentCount;
    private Integer likeCount;
}
