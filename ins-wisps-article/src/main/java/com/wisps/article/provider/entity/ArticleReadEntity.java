package com.wisps.article.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "article_read")
public class ArticleReadEntity extends BaseEntity {
    private Long articleId;
    private Long uid;
    private Long oid;
}
