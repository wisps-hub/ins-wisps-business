package com.wisps.auth.provider.assemble;

import com.wisps.auth.provider.consts.MFAMethod;
import com.wisps.auth.provider.consts.MFAMethodStatus;
import com.wisps.auth.provider.entity.SsoAccountMFaEntity;
import com.wisps.auth.provider.vo.dto.SsoMFA;

public class SsoMfaAssemble {

    public static SsoMFA convertAccountMFA(SsoAccountMFaEntity ssoAccountMFA) {
        if (ssoAccountMFA != null) {
            SsoMFA ssoMFA = new SsoMFA();
            ssoMFA.setId(ssoAccountMFA.getId());
            ssoMFA.setAccountId(ssoAccountMFA.getAccountId());
            ssoMFA.setMfaMethod(MFAMethod.getByCode(ssoAccountMFA.getMfaMethod()));
            ssoMFA.setMfaMethodStatus(MFAMethodStatus.getByCode(ssoAccountMFA.getMethodStatus()));
            ssoMFA.setCreatetime(ssoAccountMFA.getCreatetime());
            ssoMFA.setModifytime(ssoAccountMFA.getModifytime());
            return ssoMFA;
        }
        return null;
    }
}
