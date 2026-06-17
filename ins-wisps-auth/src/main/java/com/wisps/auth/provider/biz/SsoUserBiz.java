package com.wisps.auth.provider.biz;

import com.wisps.auth.provider.vo.dto.SsoUserInfo;
import com.wisps.auth.provider.vo.dto.UserAndTeamInfo;

import java.util.List;

public interface SsoUserBiz {

    List<SsoUserInfo> enableSsoUserInfos(List<String> userIdList);

    List<SsoUserInfo> ssoUserInfos(List<String> userIds);

    List<UserAndTeamInfo> getUserAndTeamInfosOrderByAccess(List<String> userIds);
}