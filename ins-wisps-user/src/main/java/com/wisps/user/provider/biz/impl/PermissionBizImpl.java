package com.wisps.user.provider.biz.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.wisps.user.api.resp.PermissionDto;
import com.wisps.user.provider.biz.PermissionBiz;
import com.wisps.user.provider.entity.PermissionEntity;
import com.wisps.user.provider.mapping.dao.PermissionDao;
import com.wisps.user.provider.vo.resp.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PermissionBizImpl implements PermissionBiz {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<PermissionDto> list() {
        List<PermissionEntity> entityList = permissionDao.list();
        if (CollectionUtils.isEmpty(entityList)) return Lists.newArrayList();
        Map<Long, List<PermissionDto>> pidMap = new HashMap<>();
        List<PermissionDto> topGrades = new ArrayList<>();
        for (PermissionEntity entity : entityList) {
            if (entity.getGrade() == 1){
                topGrades.add(BeanUtil.copyProperties(entity, PermissionDto.class));
            }else {
                List<PermissionDto> childList = pidMap.getOrDefault(entity.getPid(), new ArrayList<>());
                childList.add(BeanUtil.copyProperties(entity, PermissionDto.class));
                pidMap.put(entity.getPid(), childList);
            }
        }
        loopDto(topGrades, pidMap);
        return topGrades;
    }

    public void loopDto(List<PermissionDto> dtoList, Map<Long, List<PermissionDto>> pidMap){
        if (CollectionUtils.isEmpty(dtoList)) return;
        for (PermissionDto dto : dtoList) {
            List<PermissionDto> childList = pidMap.get(dto.getId());
            if (!CollectionUtils.isEmpty(childList)) dto.setChildren(childList);
            loopDto(childList, pidMap);
        }
    }

}
