package com.wisps.auth.provider.vo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TeamInfo {
    @Schema(title = "组织ID")
    private String id;
    @Schema(title = "组织名称")
    private String name;
    @Schema(title = "i18nName")
    private I18nName i18nName;
    @Schema(title = "组织图标")
    private String logo;
    @Schema(title = "组织状态：1正常; 2解散中; 3解散; 5冻结")
    private int status;
    @Schema(title = "组织类型：1:企业组织; 2:个人组织")
    private int type;
    @Schema(title = "认证状态：0:未认证；1:已认证")
    private int authStatus;
    @Schema(title = "displayId")
    private String displayId;
    @Schema(title = "domainName")
    private String domainName;
}
