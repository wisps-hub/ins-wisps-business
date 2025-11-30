package com.wisps.user.provider.controller;

import com.wisps.controller.BaseController;
import com.wisps.req.PageReq;
import com.wisps.resp.MultiResult;
import com.wisps.resp.PageResp;
import com.wisps.resp.Result;
import com.wisps.user.provider.biz.UserBiz;
import com.wisps.user.provider.vo.req.PageUserReq;
import com.wisps.user.provider.vo.req.RealNameAuthVo;
import com.wisps.user.provider.vo.resp.UserVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {

    @Autowired
    private UserBiz userBiz;

    @GetMapping("/userInfo")
    public Result<UserVo> userInfo() {
        return Result.success(userBiz.getById(Long.valueOf(getUid())));
    }

    @PostMapping("/page")
    public MultiResult<UserVo> list(@RequestBody PageUserReq pageUserReq) {
        PageResp<UserVo> resp = userBiz.page(pageUserReq);
        return MultiResult.successMulti(resp.getDatas(), resp.getTotal(), resp.getCurrPage(), resp.getPageSize());
    }

    @PostMapping("/auth")
    public Result<Boolean> auth(@Valid @RequestBody RealNameAuthVo realNameAuthVo) {
        //todo wisps
        //实名
        //上链
        return Result.success(true);
    }

}