package com.wisps.auth.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.auth.provider.entity.SsoAccountMFaEntity;
import com.wisps.auth.provider.mapping.dao.SsoAccountMFaDao;
import com.wisps.auth.provider.mapping.mapper.SsoAccountMFaMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SsoAccountMFaDaoImpl extends ServiceImpl<SsoAccountMFaMapper, SsoAccountMFaEntity> implements SsoAccountMFaDao {

    @Override
    public List<SsoAccountMFaEntity> listByAccountId(String accountId) {
        return this.lambdaQuery().eq(SsoAccountMFaEntity::getAccountId, accountId).list();
    }

}