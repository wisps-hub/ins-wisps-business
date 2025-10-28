package com.wisps.article.provider.biz;

import com.wisps.article.provider.vo.req.ArticleQueryReq;
import com.wisps.article.provider.vo.req.ArticleReq;
import com.wisps.article.provider.vo.resp.ArticleVo;
import com.wisps.resp.MultiResult;

public interface ArticleBiz {
    Boolean save(ArticleReq articleReq);

    MultiResult<ArticleVo> pageQuery(ArticleQueryReq queryReq);

    Boolean delete(Long id, Long uid);

    ArticleVo getArticle(Long id, Long uid);
}
