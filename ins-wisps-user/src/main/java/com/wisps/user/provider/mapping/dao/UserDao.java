package com.wisps.user.provider.mapping.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisps.user.provider.entity.UserEntity;

import java.util.List;

public interface UserDao extends IService<UserEntity> {
    UserEntity selectById(Long id);
    List<UserEntity> getList(List<Long> ids);
    UserEntity getByInviteCode(String inviteCode);
    UserEntity getByMobile(String mobile);
    UserEntity register(String telephone, String nickName, String password, String inviteCode, Long inviterId);
    UserEntity conditionQuery(Long uid, String mobile, String password);
}