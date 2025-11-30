package com.wisps.user.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.user.provider.entity.PermissionEntity;
import com.wisps.user.provider.mapping.dao.PermissionDao;
import com.wisps.user.provider.mapping.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

@Service
public class PermissionDaoImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionDao {
}