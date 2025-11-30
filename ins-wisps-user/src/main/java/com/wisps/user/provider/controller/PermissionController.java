package com.wisps.user.provider.controller;

import com.wisps.controller.BaseController;
import com.wisps.resp.MultiResp;
import com.wisps.user.api.resp.PermissionDto;
import com.wisps.user.api.resp.RoleDto;
import com.wisps.user.provider.biz.PermissionBiz;
import com.wisps.user.provider.biz.RoleBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionBiz permissionBiz;

    @GetMapping("/list")
    public MultiResp<PermissionDto> list() {
        return MultiResp.of(permissionBiz.list());
    }

}