package com.wisps.auth.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.auth.provider.consts.CredentialType;
import com.wisps.auth.provider.entity.SsoAccountEntity;
import com.wisps.auth.provider.mapping.dao.SsoAccountDao;
import com.wisps.auth.provider.mapping.mapper.SsoAccountMapper;
import org.springframework.stereotype.Component;

@Component
public class SsoAccountDaoImpl extends ServiceImpl<SsoAccountMapper, SsoAccountEntity> implements SsoAccountDao {

    @Override
    public SsoAccountEntity getAccountByIdentity(String logInIdentity, CredentialType credentialType) {
        QueryWrapper<SsoAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SsoAccountEntity.Column.loginIdentity, logInIdentity);
        queryWrapper.eq(SsoAccountEntity.Column.identityType, credentialType.getType());
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean insertAccount(String id, String dbIdentity, CredentialType credentialType) {
        return baseMapper.insert(new SsoAccountEntity(id, dbIdentity, credentialType.getType())) > 0;
    }

    @Override
    public boolean mfaEnable(String accountId) {
        SsoAccountEntity ssoAccountEntity = this.getById(accountId);
        return ssoAccountEntity != null && ssoAccountEntity.getEnableMfa();
    }
}
