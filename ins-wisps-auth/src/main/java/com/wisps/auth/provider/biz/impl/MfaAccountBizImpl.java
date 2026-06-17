package com.wisps.auth.provider.biz.impl;

import com.google.common.collect.ImmutableList;
import com.wisps.auth.provider.assemble.SsoMfaAssemble;
import com.wisps.auth.provider.biz.MfaAccountBiz;
import com.wisps.auth.provider.consts.MFAMethodStatus;
import com.wisps.auth.provider.entity.SsoAccountMFaEntity;
import com.wisps.auth.provider.mapping.dao.SsoAccountDao;
import com.wisps.auth.provider.mapping.dao.SsoAccountMFaDao;
import com.wisps.auth.provider.vo.dto.AccountMfaSetting;
import com.wisps.auth.provider.vo.dto.SsoMFA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MfaAccountBizImpl implements MfaAccountBiz {

    @Autowired
    private SsoAccountMFaDao ssoAccountMFaDao;
    @Autowired
    private SsoAccountDao ssoAccountDao;

    @Override
    public AccountMfaSetting getAccountMfa(String accountId) {
        AccountMfaSetting accountMfaSetting = new AccountMfaSetting();
        accountMfaSetting.setAccountId(accountId);
        MFAMethodStatus status = checkEnableMFA(accountId);
        List<SsoMFA> ssoMfaList = this.selectMFAMethod(accountId);
        boolean enableMFA = ssoMfaList.stream().anyMatch(it -> !MFAMethodStatus.CLOSE.equals(it.getMfaMethodStatus()));
        if (enableMFA) {
            accountMfaSetting.setStatus(status);
            accountMfaSetting.setSsoMFAList(ssoMfaList);
        } else {
            accountMfaSetting.setStatus(MFAMethodStatus.CLOSE);
            accountMfaSetting.setSsoMFAList(ImmutableList.of());
        }
        return accountMfaSetting;
    }

    @Override
    public MFAMethodStatus checkEnableMFA(String accountId) {
        return ssoAccountDao.mfaEnable(accountId) ? MFAMethodStatus.OPEN : MFAMethodStatus.CLOSE;
    }

    @Override
    public List<SsoMFA> selectMFAMethod(String accountId) {
        return ssoAccountMFaDao.listByAccountId(accountId)
                .stream().map(SsoMfaAssemble::convertAccountMFA)
                .collect(Collectors.toList());
    }
}