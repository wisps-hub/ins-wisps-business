package com.wisps.auth.provider.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfoWithTeamVo extends UserInfoVo {
    @Schema(description = "组织信息")
    private TeamInfoVo teamInfo;
}
