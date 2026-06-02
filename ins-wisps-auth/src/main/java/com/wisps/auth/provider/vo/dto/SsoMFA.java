package com.wisps.auth.provider.vo.dto;

import com.wisps.auth.provider.consts.MFAMethod;
import com.wisps.auth.provider.consts.MFAMethodStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SsoMFA {

    private String id;

    private String accountId;

    private MFAMethod mfaMethod;

    private MFAMethodStatus mfaMethodStatus;

    private LocalDateTime createtime;

    private LocalDateTime modifytime;
}
