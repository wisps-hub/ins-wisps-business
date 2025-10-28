package com.wisps.article.provider.biz.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.wisps.article.provider.biz.ChannelBiz;
import com.wisps.article.provider.entity.ChannelEntity;
import com.wisps.article.provider.mapping.dao.ChannelDao;
import com.wisps.article.provider.vo.resp.ChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ChannelBizImpl implements ChannelBiz {

    @Autowired
    private ChannelDao channelDao;

    @Override
    public List<ChannelVo> channelList(Long uid) {
        QueryWrapper<ChannelEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        List<ChannelEntity> list = channelDao.getBaseMapper().selectList(queryWrapper);
        return CollectionUtils.isEmpty(list)? Lists.newArrayList() :
                list.stream().map(article -> BeanUtil.copyProperties(article, ChannelVo.class)).toList();
    }

}
