package com.wisps.article.provider.biz;

import com.wisps.article.provider.vo.resp.ChannelVo;

import java.util.List;

public interface ChannelBiz {
    List<ChannelVo> channelList(Long uid);
}
