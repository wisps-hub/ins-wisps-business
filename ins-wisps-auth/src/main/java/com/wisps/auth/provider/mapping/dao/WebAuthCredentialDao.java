package com.wisps.auth.provider.mapping.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisps.auth.provider.entity.WebAuthCredentialEntity;

import java.util.List;

public interface WebAuthCredentialDao extends IService<WebAuthCredentialEntity> {
    List<WebAuthCredentialEntity> listByAccountId(String accountId);
}
