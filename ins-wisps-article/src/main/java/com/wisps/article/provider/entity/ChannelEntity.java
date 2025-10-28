package com.wisps.article.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "article_channel")
public class ChannelEntity extends BaseEntity {
    private String channelName;
    private Integer levelCode;
    private Long uid;
    private Long oid;
}
