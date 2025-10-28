package com.wisps.user.provider.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.domain.entity.BaseEntity;
import com.wisps.datasource.handler.AesEncryptTypeHandler;
import com.wisps.user.api.consts.UserRole;
import com.wisps.user.api.consts.UserState;
import com.wisps.user.api.req.ModifyReq;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Getter
@Setter
@TableName(value = "org_user", autoResultMap = true)
public class UserEntity extends BaseEntity {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
    @TableField(typeHandler = AesEncryptTypeHandler.class)
    private String passwordEncrypt;
    /**
     * 状态
     */
    private UserState state;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 邀请人用户ID
     */
    private Long inviterId;
    /**
     * 手机号
     */
    @TableField(typeHandler = AesEncryptTypeHandler.class)
    private String mobile;
    /**
     * 邮箱
     */
    @TableField(typeHandler = AesEncryptTypeHandler.class)
    private String email;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 区块链地址
     */
    private String blockChainUrl;
    /**
     * 区块链平台
     */
    private String blockChainPlatform;
    /**
     * 实名认证
     */
    private Boolean certification;
    /**
     * 真实姓名
     */
    @TableField(typeHandler = AesEncryptTypeHandler.class)
    private String realName;
    /**
     * 身份证hash
     */
    @TableField(typeHandler = AesEncryptTypeHandler.class)
    private String idCardNo;
    /**
     * 用户角色
     */
    private UserRole userRole;

    public UserEntity register(String mobile, String nickName, String password, String inviteCode, Long inviterId) {
        this.setMobile(mobile);
        this.setNickName(nickName);
        this.setPasswordEncrypt(password);
        this.setState(UserState.INIT);
        this.setUserRole(UserRole.CUSTOMER);
        this.setInviteCode(inviteCode);
        this.setInviterId(inviterId);
        return this;
    }

    public boolean canModifyInfo() {
        return state == UserState.INIT || state == UserState.AUTH || state == UserState.ACTIVE;
    }

    public UserEntity modify(ModifyReq modifyReq) {
        if (StringUtils.isNotBlank(modifyReq.getMobile())){
            this.setMobile(modifyReq.getMobile());
        }
        if (StringUtils.isNotBlank(modifyReq.getNickName())){
            this.setNickName(modifyReq.getNickName());
        }
        if (StringUtils.isNotBlank(modifyReq.getAvatarUrl())){
            this.setAvatarUrl(modifyReq.getAvatarUrl());
        }
        if (StringUtils.isNotBlank(modifyReq.getPassword())){
            this.setPasswordEncrypt(modifyReq.getPassword());
        }
        return this;
    }

    public UserEntity realNameAuth(String realName, String idCard) {
        this.setRealName(realName);
        this.setIdCardNo(idCard);
        this.setCertification(true);
        this.setState(UserState.AUTH);
        return this;
    }

    public UserEntity active(String blockChainUrl, String blockChainPlatform) {
        this.setBlockChainUrl(blockChainUrl);
        this.setBlockChainPlatform(blockChainPlatform);
        this.setState(UserState.ACTIVE);
        return this;
    }
}