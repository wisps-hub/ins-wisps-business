package com.wisps.auth.provider.vo.dto;

import com.wisps.auth.provider.consts.MFAMethodStatus;
import lombok.Data;

import java.util.List;

@Data
public class AccountMfaSetting {
    private String accountId;

    private List<SsoMFA> ssoMFAList;

    private MFAMethodStatus status;
}
