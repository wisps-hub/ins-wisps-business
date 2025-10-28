package com.wisps.article.provider.biz.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.wisps.article.provider.biz.ArticleBiz;
import com.wisps.article.provider.entity.ArticleEntity;
import com.wisps.article.provider.mapping.dao.ArticleDao;
import com.wisps.article.provider.vo.req.ArticleQueryReq;
import com.wisps.article.provider.vo.req.ArticleReq;
import com.wisps.article.provider.vo.resp.ArticleVo;
import com.wisps.resp.MultiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleBizImpl implements ArticleBiz {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Boolean save(ArticleReq articleReq) {
        return true;
    }

    @Override
    public MultiResult<ArticleVo> pageQuery(ArticleQueryReq queryReq) {
        Page<ArticleEntity> page = articleDao.pageQuery(queryReq.getCurrPage(), queryReq.getPageSize(),
                queryReq.getStatus(), queryReq.getChannelId(), queryReq.getBeginPubDate(),
                queryReq.getEndPubDate(), queryReq.getUid());
        List<ArticleVo> list = (CollectionUtils.isEmpty(page.getRecords()))? Lists.newArrayList() : page.getRecords()
                .stream().map(entity -> BeanUtil.copyProperties(entity, ArticleVo.class)).collect(Collectors.toList());
        return MultiResult.successMulti(list, page.getTotal(), queryReq.getCurrPage(), queryReq.getPageSize());
    }

    @Override
    public Boolean delete(Long id, Long uid) {
        ArticleEntity article = articleDao.getById(id);
        if (!article.getUid().equals(uid)) {
            return false;
        }
        articleDao.removeById(id);
        return true;
    }

    @Override
    public ArticleVo getArticle(Long id, Long uid) {
        ArticleEntity article = articleDao.getById(id);
        if (!article.getUid().equals(uid)) {
            return new ArticleVo();
        }
        return BeanUtil.copyProperties(article, ArticleVo.class);
    }
}
