package com.wisps.auth.provider.vo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserAndTeamInfo extends UserInfo{
    @Schema(title = "组织信息")
    private TeamInfo teamInfo;
}
