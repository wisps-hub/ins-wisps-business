package com.wisps.gateway.filter;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.wisps.user.api.consts.UserPerm;
import com.wisps.user.api.consts.UserRole;
import com.wisps.user.api.consts.UserState;
import com.wisps.user.api.resp.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        UserDto userInfo = (UserDto) StpUtil.getSessionByLoginId(loginId).get((String) loginId);

        if (userInfo.getUserRole() == UserRole.ADMIN
                || userInfo.getState().equals(UserState.ACTIVE.name())
                || userInfo.getState().equals(UserState.AUTH.name()) ) {
            return List.of(UserPerm.BASIC.name(), UserPerm.AUTH.name());
        }

        if (userInfo.getState().equals(UserState.INIT.name())) {
            return List.of(UserPerm.BASIC.name());
        }

        if (userInfo.getState().equals(UserState.FROZEN.name())) {
            return List.of(UserPerm.FROZEN.name());
        }

        return List.of(UserPerm.NONE.name());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        UserDto userInfo = (UserDto) StpUtil.getSessionByLoginId(loginId).get((String) loginId);
        if (userInfo.getUserRole() == UserRole.ADMIN) {
            return List.of(UserRole.ADMIN.name());
        }
        return List.of(UserRole.CUSTOMER.name());
    }
}
