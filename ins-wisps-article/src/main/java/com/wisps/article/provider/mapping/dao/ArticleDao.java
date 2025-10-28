package com.wisps.article.provider.mapping.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wisps.article.provider.entity.ArticleEntity;

public interface ArticleDao extends IService<ArticleEntity> {
    Page<ArticleEntity> pageQuery(Integer currPage, Integer pageSize, Integer status, Long channelId, String beginPubDate, String endPubDate, Long uid);
}
