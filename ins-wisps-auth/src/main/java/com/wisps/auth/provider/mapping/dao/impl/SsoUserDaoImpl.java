package com.wisps.auth.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.auth.provider.entity.SsoAccountEntity;
import com.wisps.auth.provider.entity.SsoUserEntity;
import com.wisps.auth.provider.mapping.dao.SsoUserDao;
import com.wisps.auth.provider.mapping.mapper.SsoUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SsoUserDaoImpl extends ServiceImpl<SsoUserMapper, SsoUserEntity> implements SsoUserDao {

    @Override
    public List<SsoUserEntity> listByAccountIds(List<String> accountIds) {
        QueryWrapper<SsoUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(SsoUserEntity.Column.accountId, accountIds);
        return baseMapper.selectList(queryWrapper);
    }
}
