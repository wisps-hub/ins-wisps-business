package com.wisps.user.provider.biz.impl;

import com.google.common.collect.Lists;
import com.wisps.user.api.resp.RoleDto;
import com.wisps.user.provider.biz.RoleBiz;
import com.wisps.user.provider.entity.PermissionEntity;
import com.wisps.user.provider.entity.RoleEntity;
import com.wisps.user.provider.mapping.dao.PermissionDao;
import com.wisps.user.provider.mapping.dao.RoleDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleBizImpl implements RoleBiz {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<RoleDto> list() {
        List<RoleEntity> entityList = roleDao.list();
        if (CollectionUtils.isEmpty(entityList)) return Lists.newArrayList();
        Map<Long, String> permMap = permissionDao.list().stream().collect(Collectors
                .toMap(PermissionEntity::getId, PermissionEntity::getPath, (k1, k2) -> k1));

        List<RoleDto> resList = entityList.stream().map(role->{
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setTitle(role.getTitle());
            roleDto.setRoleType(role.getRoleType());
            if (StringUtils.isNotBlank(role.getPerms())) {
                List<String> perms = Arrays.stream(role.getPerms().split(","))
                        .map(Long::valueOf).map(permMap::get).toList();
                roleDto.setPerms(perms);
            }
            return roleDto;
        }).collect(Collectors.toList());
        return resList;
    }
}
