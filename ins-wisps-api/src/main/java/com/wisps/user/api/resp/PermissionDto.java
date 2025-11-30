package com.wisps.user.api.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto implements Serializable {
    private Long id;
    private String path;
    private String title;
    private Integer grade;
    private Integer permType;
    private Long pid;
    private List<PermissionDto> children;
}
