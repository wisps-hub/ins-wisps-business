package com.wisps.user.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
@TableName(value = "org_role", autoResultMap = true)
public class RoleEntity extends BaseEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private Integer roleType;
    /**
     * 父id
     */
    private String perms;

    public RoleEntity create(String title, Integer type, List<String> permissionIds) {
        this.setTitle(title);
        this.setRoleType(type);
        if (!CollectionUtils.isEmpty(permissionIds)){
            this.setPerms(String.join(",", permissionIds));
        }
        return this;
    }
}