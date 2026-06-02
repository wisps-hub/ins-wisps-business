package com.wisps.auth.provider.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrcodeStatusWebLoginMergeResp implements Serializable {
    private String authCode;
    private Integer loginScene;
    private QrcodeStatusWebLoginResp qrcodeStatusWebLoginResp;
    private String loginContextId;
}
