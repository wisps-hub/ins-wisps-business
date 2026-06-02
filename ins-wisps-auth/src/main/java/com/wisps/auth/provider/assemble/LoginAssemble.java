package com.wisps.auth.provider.assemble;

import com.google.common.collect.ImmutableList;
import com.wisps.auth.provider.utils.CommonUtil;
import com.wisps.auth.provider.vo.dto.*;
import com.wisps.auth.provider.vo.resp.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LoginAssemble {

    public static LoginResp toLoginResp(LoginResult loginResult) {
        LoginResp loginResultResVo = new LoginResp();
        loginResultResVo.setAppToken(loginResult.getAppToken());
        loginResultResVo.setUserList(toLoginUserInfoVoList(loginResult.getUserList()));
        loginResultResVo.setMfaStatus(loginResult.getMfaStatus().getCode());
        List<SsoMFA> ssoMFAList = loginResult.getSsoMFAList();
        List<SsoMfaVo> collect = ssoMFAList.stream()
                .map(it -> {
                    SsoMfaVo ssoMfaVo = new SsoMfaVo();
                    ssoMfaVo.setMfaMethodCode(it.getMfaMethod().getCode());
                    ssoMfaVo.setMfaMethodName(it.getMfaMethod().getName());
                    return ssoMfaVo;
                }).collect(Collectors.toList());
        loginResultResVo.setSsoMfaVoList(collect);
        return loginResultResVo;
    }

    public static List<LoginUserInfoVo> toLoginUserInfoVoList(List<LoginUserInfo> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return ImmutableList.of();
        }
        return userList.stream().map(LoginAssemble::toLoginUserInfoVo).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static LoginUserInfoVo toLoginUserInfoVo(LoginUserInfo loginUserInfo) {
        if (loginUserInfo == null) {
            return null;
        }
        LoginUserInfoVo loginUserInfoVo = new LoginUserInfoVo();
        loginUserInfoVo.setAppTokenExpire(loginUserInfo.getAppTokenExpire());
        loginUserInfoVo.setAppToken(loginUserInfo.getAppToken());
        loginUserInfoVo.setActiveExpire(loginUserInfo.getActiveExpire());
        loginUserInfoVo.setRefreshToken(loginUserInfo.getRefreshToken());
        loginUserInfoVo.setImToken(loginUserInfo.getImToken());
        loginUserInfoVo.setUserInfo(toLoginUserInfoVo(loginUserInfo.getUserInfo()));
        return loginUserInfoVo;
    }

    public static UserInfoWithTeamVo toLoginUserInfoVo(UserAndTeamInfo userAndTeamInfo) {
        if (userAndTeamInfo == null) {
            return null;
        }
        UserInfoWithTeamVo userAndTeamInfoVo = new UserInfoWithTeamVo();
        userAndTeamInfoVo.setTeamInfo(toTeamInfoVo(userAndTeamInfo.getTeamInfo()));
        userAndTeamInfoVo.setUid(userAndTeamInfo.getUid());
        userAndTeamInfoVo.setOid(userAndTeamInfo.getOid());
        userAndTeamInfoVo.setVer(userAndTeamInfo.getVer());
        userAndTeamInfoVo.setStatus(userAndTeamInfo.getStatus());
        userAndTeamInfoVo.setRole(userAndTeamInfo.getRole());
        userAndTeamInfoVo.setName(userAndTeamInfo.getName());
        userAndTeamInfoVo.setI18nName(userAndTeamInfo.getI18nName());
        userAndTeamInfoVo.setAvatarUrl(userAndTeamInfo.getAvatarUrl());
        userAndTeamInfoVo.setAvatarUrlsVo(toAvatarUrlsVo(userAndTeamInfo.getAvatarUrls()));
        userAndTeamInfoVo.setRegion(userAndTeamInfo.getRegion());
        if (StringUtils.isNotBlank(userAndTeamInfo.getMobile())) {
            userAndTeamInfoVo.setMobile(CommonUtil.encrypt(userAndTeamInfo.getMobile()));
        }
        if (StringUtils.isNotBlank(userAndTeamInfo.getEmail())) {
            userAndTeamInfoVo.setEmail(CommonUtil.encrypt(userAndTeamInfo.getEmail()));
        }
        userAndTeamInfoVo.setActiveTime(userAndTeamInfo.getActiveTime());
        return userAndTeamInfoVo;
    }

    public static TeamInfoVo toTeamInfoVo(TeamInfo teamInfo) {
        if (teamInfo == null) {
            return null;
        }
        TeamInfoVo teamInfoVo = new TeamInfoVo();
        teamInfoVo.setId(teamInfo.getId());
        teamInfoVo.setName(teamInfo.getName());
        teamInfoVo.setI18nName(teamInfo.getI18nName());
        teamInfoVo.setLogo(teamInfo.getLogo());
        teamInfoVo.setStatus(teamInfo.getStatus());
        teamInfoVo.setType(teamInfo.getType());
        teamInfoVo.setAuthStatus(teamInfo.getAuthStatus());
        teamInfoVo.setDisplayId(teamInfo.getDisplayId());
        teamInfoVo.setDomainName(teamInfo.getDomainName());
        return teamInfoVo;
    }

    public static AvatarUrlsVo toAvatarUrlsVo(AvatarUrls avatarUrls) {
        if (avatarUrls == null) {
            return null;
        }
        AvatarUrlsVo avatarUrlsVo = new AvatarUrlsVo();
        avatarUrlsVo.setOriginal(avatarUrls.getOriginal());
        avatarUrlsVo.setHd500(avatarUrls.getHd500());
        avatarUrlsVo.setHd150(avatarUrls.getHd150());
        return avatarUrlsVo;
    }
}
