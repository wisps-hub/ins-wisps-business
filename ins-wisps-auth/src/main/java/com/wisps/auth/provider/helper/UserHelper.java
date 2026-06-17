package com.wisps.auth.provider.helper;

import com.google.common.collect.Lists;
import com.wisps.auth.provider.vo.dto.UserAndTeamInfo;
import com.wisps.user.api.req.RegisterReq;
import com.wisps.user.api.req.UserQueryReq;
import com.wisps.user.api.resp.UserDto;
import com.wisps.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserHelper {

    @DubboReference(version = "1.0.0")
    private UserService userService;

    public UserDto register(RegisterReq registerReq){
        return userService.register(registerReq);
    }

    public UserDto queryOne(UserQueryReq queryReq){
        return userService.queryOne(queryReq);
    }

    public List<UserAndTeamInfo> getUserAndTeamInfos(List<String> userIds){
        //todo
        return Lists.newArrayList();
    }
}