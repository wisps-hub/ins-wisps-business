package com.wisps.auth.provider.assemble;

import com.wisps.auth.provider.entity.SsoUserInfoEntity;
import com.wisps.auth.provider.vo.dto.SsoUserInfo;

public class SsoUseAssemble {

    public static SsoUserInfo toSsoUserInfo(SsoUserInfoEntity entity){
        if(entity == null){
            return null;
        }
        SsoUserInfo ssoUserInfo = new SsoUserInfo();
        ssoUserInfo.setUid(entity.getUid());
        ssoUserInfo.setOid(entity.getOid());
        ssoUserInfo.setSecret(entity.getSecret());
        ssoUserInfo.setStatus(entity.getStatus());
        ssoUserInfo.setCreatetime(entity.getCreatetime());
        ssoUserInfo.setModifytime(entity.getModifytime());
        return ssoUserInfo;
    }

}
