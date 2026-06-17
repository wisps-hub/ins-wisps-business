package com.wisps.auth.provider.mapping.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisps.auth.provider.entity.SsoAccountMFaEntity;

import java.util.List;

public interface SsoAccountMFaDao extends IService<SsoAccountMFaEntity> {

    List<SsoAccountMFaEntity>  listByAccountId(String accountId);

}