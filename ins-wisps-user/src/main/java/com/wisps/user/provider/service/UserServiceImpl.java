package com.wisps.user.provider.service;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.wisps.user.api.req.*;
import com.wisps.user.api.resp.UserDto;
import com.wisps.user.api.service.UserService;
import com.wisps.user.provider.biz.UserBiz;
import com.wisps.user.provider.vo.resp.UserVo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserBiz userBiz;

    @Override
    public UserDto register(RegisterReq registerReq) {
        UserVo userVo = userBiz.register(registerReq);
        return userVo != null ? BeanUtil.copyProperties(userVo, UserDto.class) : null;
    }

    @Override
    public UserDto modify(ModifyReq modifyReq) {
        UserVo userVo = userBiz.modify(modifyReq);
        return userVo != null ? BeanUtil.copyProperties(userVo, UserDto.class) : null;
    }

    @Override
    public void realNameAuth(RealNameAuthReq realNameAuthReq) {
        userBiz.realNameAuth(realNameAuthReq);
    }

    @Override
    public void active(ActiveReq activeReq) {
        userBiz.active(activeReq);
    }

    @Override
    public void freeze(Long uid) {
        userBiz.freeze(uid);
    }

    @Override
    public void unfreeze(Long uid) {
        userBiz.unfreeze(uid);
    }

    @Override
    public UserDto queryOne(UserQueryReq queryReq) {
        UserVo userVo = userBiz.queryOne(queryReq);
        return userVo != null ? BeanUtil.copyProperties(userVo, UserDto.class) : null;
    }

    @Override
    public UserDto getById(Long id) {
        UserVo userVo = userBiz.getById(id);
        return userVo != null ? BeanUtil.copyProperties(userVo, UserDto.class) : null;
    }

    @Override
    public List<UserDto> getList(List<Long> ids) {
        List<UserVo> list = userBiz.getList(ids);
        return CollectionUtils.isEmpty(list)? Lists.newArrayList() :
                list.stream().map(userVo -> BeanUtil.copyProperties(userVo, UserDto.class)).toList();
    }

}