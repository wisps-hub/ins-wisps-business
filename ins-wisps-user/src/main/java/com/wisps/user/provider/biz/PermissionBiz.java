package com.wisps.user.provider.biz;

import com.wisps.user.api.resp.PermissionDto;

import java.util.List;

public interface PermissionBiz {
    List<PermissionDto> list();
}