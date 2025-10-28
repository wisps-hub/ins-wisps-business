package com.wisps.article.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.article.provider.entity.ArticleEntity;
import com.wisps.article.provider.mapping.dao.ArticleDao;
import com.wisps.article.provider.mapping.mapper.ArticleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ArticleDaoImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleDao {
    @Override
    public Page<ArticleEntity> pageQuery(Integer currPage, Integer pageSize, Integer status,
                                         Long channelId, String beginPubDate, String endPubDate, Long uid) {
        Page<ArticleEntity> page = new Page<>(currPage, pageSize);
        QueryWrapper<ArticleEntity> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (channelId != null && channelId > 0){
            wrapper.eq("channel_id", channelId);
        }
        if (StringUtils.isNotBlank(beginPubDate)) {
            wrapper.gt("pub_time", beginPubDate);
        }
        if (StringUtils.isNotBlank(beginPubDate)) {
            wrapper.lt("pub_time", endPubDate);
        }
        wrapper.eq("uid", uid);
        wrapper.orderBy(true, false, "createtime");
        return this.page(page, wrapper);
    }
}