package com.wisps.article.provider.controller;

import com.wisps.article.provider.biz.ArticleBiz;
import com.wisps.article.provider.vo.req.ArticleQueryReq;
import com.wisps.article.provider.vo.req.ArticleReq;
import com.wisps.article.provider.vo.resp.ArticleVo;
import com.wisps.controller.BaseController;
import com.wisps.resp.MultiResult;
import com.wisps.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleBiz articleBiz;

    @PostMapping("/save")
    public Result create(@RequestBody @Validated ArticleReq articleReq){
        log.info("articleReq: {}", articleReq);
        Long uid = Long.valueOf(getUid());
        Boolean ok = articleBiz.save(articleReq);
        return Result.success(ok);
    }

    @GetMapping("/{id}")
    public Result<ArticleVo> getArticle(@PathVariable("id") Long id){
        Long uid = Long.valueOf(getUid());
        return Result.success(articleBiz.getArticle(id, uid));
    }

    @PostMapping("/pageQuery")
    public MultiResult<ArticleVo> pageQuery(@RequestBody ArticleQueryReq queryReq){
        if (queryReq.getCurrPage() < 0){
            queryReq.setCurrPage(0);
        }
        if (queryReq.getPageSize() <= 0){
            queryReq.setPageSize(10);
        }
        queryReq.setUid(Long.valueOf(getUid()));
        return articleBiz.pageQuery(queryReq);
    }

    @DeleteMapping("/delete/{id}")
    public Result pageQuery(@PathVariable("id") Long id){
        Boolean ok = articleBiz.delete(id, Long.valueOf(getUid()));
        return Result.success(ok);
    }

}
