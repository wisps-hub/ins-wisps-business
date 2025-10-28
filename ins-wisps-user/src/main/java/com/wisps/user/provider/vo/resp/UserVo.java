package com.wisps.user.provider.vo.resp;

import com.wisps.user.api.consts.UserRole;
import com.wisps.user.api.consts.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo implements Serializable {
    private Long id;
    private String nickName;
    private String passwordEncrypt;
    private UserState state;
    private String inviteCode;
    private String inviterId;
    private String mobile;
    private String email;
    private Date lastLoginTime;
    private String avatarUrl;
    private String blockChainUrl;
    private String blockChainPlatform;
    private Boolean certification;
    private String realName;
    private String idCardNo;
    private UserRole userRole;
    private Date createtime;
    private Date modifytime;
}
