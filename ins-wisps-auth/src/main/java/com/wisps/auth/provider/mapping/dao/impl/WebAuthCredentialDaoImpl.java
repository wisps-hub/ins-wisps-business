package com.wisps.auth.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.auth.provider.entity.WebAuthCredentialEntity;
import com.wisps.auth.provider.mapping.dao.WebAuthCredentialDao;
import com.wisps.auth.provider.mapping.mapper.WebAuthCredentialMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebAuthCredentialDaoImpl extends ServiceImpl<WebAuthCredentialMapper, WebAuthCredentialEntity> implements WebAuthCredentialDao {
    @Override
    public List<WebAuthCredentialEntity> listByAccountId(String accountId) {
        QueryWrapper<WebAuthCredentialEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WebAuthCredentialEntity.Column.accountId, accountId);
        return baseMapper.selectList(queryWrapper);
    }
}
