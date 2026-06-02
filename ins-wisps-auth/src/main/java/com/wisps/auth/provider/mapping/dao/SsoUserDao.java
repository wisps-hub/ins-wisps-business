package com.wisps.auth.provider.mapping.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.ImmutableList;
import com.wisps.auth.provider.entity.SsoUserEntity;

import java.util.List;

public interface SsoUserDao extends IService<SsoUserEntity> {

    List<SsoUserEntity> listByAccountIds(List<String> accountIds);

}