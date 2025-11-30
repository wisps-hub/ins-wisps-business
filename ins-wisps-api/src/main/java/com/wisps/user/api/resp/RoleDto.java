package com.wisps.user.api.resp;

import com.wisps.user.api.consts.UserRole;
import com.wisps.user.api.consts.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto implements Serializable {
    private Long id;
    private String title;
    private Integer roleType;
    private List<String> perms;
}
