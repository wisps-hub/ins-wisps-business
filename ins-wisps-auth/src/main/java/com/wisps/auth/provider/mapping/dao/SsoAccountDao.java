package com.wisps.auth.provider.mapping.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisps.auth.provider.consts.CredentialType;
import com.wisps.auth.provider.entity.SsoAccountEntity;

public interface SsoAccountDao extends IService<SsoAccountEntity> {

    SsoAccountEntity getAccountByIdentity(String logInIdentity, CredentialType credentialType);

    boolean insertAccount(String id, String dbIdentity, CredentialType credentialType);

    boolean mfaEnable(String accountId);
}