package com.wisps.article.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.article.provider.entity.ChannelEntity;
import com.wisps.article.provider.mapping.dao.ChannelDao;
import com.wisps.article.provider.mapping.mapper.ChannelMapper;
import org.springframework.stereotype.Service;

@Service
public class ChannelDaoImpl extends ServiceImpl<ChannelMapper, ChannelEntity> implements ChannelDao {
}
