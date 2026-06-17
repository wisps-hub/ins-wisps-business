package com.wisps.auth.provider.biz;

import com.wisps.auth.provider.consts.MFAMethodStatus;
import com.wisps.auth.provider.vo.dto.AccountMfaSetting;
import com.wisps.auth.provider.vo.dto.SsoMFA;

import java.util.List;

public interface MfaAccountBiz {

    AccountMfaSetting getAccountMfa(String accountId);

    MFAMethodStatus checkEnableMFA(String accountId);

    List<SsoMFA> selectMFAMethod(String accountId);
}