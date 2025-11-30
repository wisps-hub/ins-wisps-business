package com.wisps.user.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "org_permission", autoResultMap = true)
public class PermissionEntity extends BaseEntity {
    /**
     * eg /home
     */
    private String path;
    /**
     * 标题
     */
    private String title;
    /**
     * 层级
     */
    private Integer grade;
    /**
     * 类型
     */
    private Integer permType;
    /**
     * 父id
     */
    private Long pid;

    public PermissionEntity create(String path, String title, Integer grade, Integer type, Long pid) {
        this.setPath(path);
        this.setTitle(title);
        this.setGrade(grade);
        this.setPermType(type);
        this.setPid(pid);
        return this;
    }
}