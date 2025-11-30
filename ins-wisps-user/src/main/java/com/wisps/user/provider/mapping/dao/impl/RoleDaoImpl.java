package com.wisps.user.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.user.provider.entity.RoleEntity;
import com.wisps.user.provider.mapping.dao.RoleDao;
import com.wisps.user.provider.mapping.mapper.RoleMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleDaoImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleDao {
}