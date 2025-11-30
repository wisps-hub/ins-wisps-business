package com.wisps.user.provider.controller;

import com.wisps.controller.BaseController;
import com.wisps.resp.MultiResp;
import com.wisps.resp.MultiResult;
import com.wisps.resp.PageResp;
import com.wisps.resp.Result;
import com.wisps.user.api.resp.RoleDto;
import com.wisps.user.provider.biz.RoleBiz;
import com.wisps.user.provider.biz.UserBiz;
import com.wisps.user.provider.vo.req.PageUserReq;
import com.wisps.user.provider.vo.req.RealNameAuthVo;
import com.wisps.user.provider.vo.resp.UserVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleBiz roleBiz;

    @GetMapping("/list")
    public MultiResp<RoleDto> list() {
        return MultiResp.of(roleBiz.list());
    }

}