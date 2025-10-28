package com.wisps.article.provider.controller;

import com.wisps.article.provider.biz.ChannelBiz;
import com.wisps.article.provider.vo.resp.ChannelVo;
import com.wisps.controller.BaseController;
import com.wisps.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController extends BaseController {

    @Autowired
    private ChannelBiz channelBiz;

    @GetMapping("/channels")
    public Result channels(){
        Long uid = Long.valueOf(getUid());
        List<ChannelVo> list = channelBiz.channelList(uid);
        return Result.success(list);
    }
}
