package com.wisps.auth.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.auth.provider.entity.SsoUserInfoEntity;
import com.wisps.auth.provider.mapping.dao.SsoUserInfoDao;
import com.wisps.auth.provider.mapping.mapper.SsoUserInfoMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SsoUserInfoDaoImpl extends ServiceImpl<SsoUserInfoMapper, SsoUserInfoEntity> implements SsoUserInfoDao {

    @Override
    public List<SsoUserInfoEntity> listByUids(List<String> uids) {
        return  this.lambdaQuery().in(SsoUserInfoEntity::getUid, uids).list();
    }

}