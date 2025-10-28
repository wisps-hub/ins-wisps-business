package com.wisps.user.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.datasource.utils.AesUtil;
import com.wisps.user.provider.entity.UserEntity;
import com.wisps.user.provider.mapping.dao.UserDao;
import com.wisps.user.provider.mapping.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDaoImpl extends ServiceImpl<UserMapper, UserEntity> implements UserDao {

    @Override
    public UserEntity selectById(Long id) {
        return getById(id);
    }

    @Override
    public List<UserEntity> getList(List<Long> ids) {
        return baseMapper.getList(ids);
    }

    @Override
    public UserEntity getByInviteCode(String inviteCode) {
        if (StringUtils.isBlank(inviteCode)) {
            return null;
        }
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invite_code", inviteCode);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public UserEntity getByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", AesUtil.encrypt(mobile));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public UserEntity register(String mobile, String nickName, String password, String inviteCode, Long inviterId) {
        UserEntity user = new UserEntity();
        return save(user.register(mobile, nickName, password, inviteCode, inviterId))? user : null;
    }

    @Override
    public UserEntity conditionQuery(Long uid, String mobile, String password) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        if (uid != null){
            queryWrapper.eq("id", uid);
        }
        if (StringUtils.isNotBlank(mobile)){
            queryWrapper.eq("mobile", AesUtil.encrypt(mobile));
        }
        if (StringUtils.isNotBlank(password)){
            queryWrapper.eq("password_encrypt", AesUtil.encrypt(password));
        }
        return baseMapper.selectOne(queryWrapper);
    }
}