package com.wisps.article.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName(value = "article")
public class ArticleEntity extends BaseEntity {
    private String title;
    private String content;
    private Long channelId;
    private Integer coverType;
    private String images;
    private Integer status;
    private Date pubTime;
    private Long uid;
    private Long oid;
}
