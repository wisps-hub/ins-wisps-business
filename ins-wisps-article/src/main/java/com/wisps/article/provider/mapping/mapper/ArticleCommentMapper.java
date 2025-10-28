package com.wisps.article.provider.mapping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisps.article.provider.entity.ArticleCommentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleCommentEntity> {
}
