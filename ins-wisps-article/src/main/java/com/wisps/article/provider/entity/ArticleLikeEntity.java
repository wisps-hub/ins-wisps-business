package com.wisps.article.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "article_like")
public class ArticleLikeEntity extends BaseEntity {
    private Long businessId;
    private Integer scene;
    private Long uid;
    private Long oid;
}
