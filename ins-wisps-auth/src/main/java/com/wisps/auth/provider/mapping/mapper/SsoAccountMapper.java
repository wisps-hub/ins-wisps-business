package com.wisps.auth.provider.mapping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisps.auth.provider.entity.SsoAccountEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SsoAccountMapper extends BaseMapper<SsoAccountEntity> {
}
