package com.wisps.article.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "article_comment")
public class ArticleCommentEntity extends BaseEntity {
    private Long articleId;
    private Long pid;
    private String content;
    private Integer level;
    private Long uid;
    private Long oid;
}
