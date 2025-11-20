package com.wisps.user.provider.biz;

import com.wisps.resp.MultiResult;
import com.wisps.resp.PageResp;
import com.wisps.user.api.req.*;
import com.wisps.user.provider.vo.req.PageUserReq;
import com.wisps.user.provider.vo.resp.UserVo;

import java.util.List;

public interface UserBiz {
    /**
     * 注册
     */
    UserVo register(RegisterReq registerReq);

    /**
     * 修改
     */
    UserVo modify(ModifyReq modifyReq);

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
    UserVo queryOne(UserQueryReq queryReq);

    /**
     * 根据id查询用户信息
     */
    UserVo getById(Long id);

    /**
     * 批量查询用户信息
     */
    List<UserVo> getList(List<Long> ids);

    PageResp<UserVo> page(PageUserReq pageUserReq);
}