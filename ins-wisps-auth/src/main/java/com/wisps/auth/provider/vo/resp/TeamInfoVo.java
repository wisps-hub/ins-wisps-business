package com.wisps.auth.provider.vo.resp;

import com.wisps.auth.provider.vo.dto.I18nName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
public class TeamInfoVo implements Serializable {
    @Schema(description = "组织ID")
    private String id;
    @Schema(description = "团队名称")
    private String name;
    @Schema(description = "i18nName")
    private I18nName i18nName;
    @Schema(description = "团队图标")
    private String logo;
    @Schema(description = "组织状态：1正常; 2解散中; 3解散; 5冻结")
    private int status;
    @Schema(description = "组织类型：1:企业组织; 2:个人组织")
    private int type;
    @Schema(title = "认证状态：0:未认证；1:已认证")
    private int authStatus;
    @Schema(title = "displayId")
    private String displayId;
    @Schema(title = "domainName")
    private String domainName;
}