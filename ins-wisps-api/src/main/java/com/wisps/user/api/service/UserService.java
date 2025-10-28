package com.wisps.user.api.service;

import com.wisps.user.api.req.*;
import com.wisps.user.api.resp.UserDto;

import java.util.List;

public interface UserService {
    /**
     * 注册
     */
    UserDto register(RegisterReq registerReq);

    /**
     * 修改
     */
    UserDto modify(ModifyReq modifyReq);

    /**
     * 实名认证
     */
    void realNameAuth(RealNameAuthReq realNameAuthReq);

    /**
     * 用户激活
     */
    void active(ActiveReq activeReq);

    /**
     * 冻结
     */
    void freeze(Long uid);

    /**
     * 解冻
     */
    void unfreeze(Long uid);

    /**
     * 条件查询
     */
    UserDto queryOne(UserQueryReq queryReq);

    /**
     * 根据id查询用户信息
     */
    UserDto getById(Long id);

    /**
     * 批量查询用户信息
     */
    List<UserDto> getList(List<Long> ids);
}