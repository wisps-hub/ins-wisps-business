package com.wisps.auth.provider.mapping.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisps.auth.provider.entity.SsoUserInfoEntity;

import java.util.List;

public interface SsoUserInfoDao extends IService<SsoUserInfoEntity> {

    List<SsoUserInfoEntity> listByUids(List<String> uids);
}