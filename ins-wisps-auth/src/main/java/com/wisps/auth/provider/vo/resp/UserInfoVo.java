package com.wisps.auth.provider.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo implements Serializable {
    @Schema(description = "用户id")
    protected String uid;
    @Schema(description = "组织id")
    protected String oid;
    @Schema(description = "版本")
    protected String ver;
    @Schema(description = "状态")
    protected Byte status;
    @Schema(description = "角色")
    protected Integer role;
    @Schema(description = "用户在组织内的名称")
    protected String name;
    @Deprecated // keep for app old version
    @Schema(description = "i18nName")
    protected I18nNameVo i18nName;
    @Schema(description = "头像")
    protected String avatarUrl;
    @Schema(description = "头像Urls")
    protected AvatarUrlsVo avatarUrlsVo;
    @Schema(description = "国家码")
    protected String region;
    @Schema(description = "手机号-加密")
    protected String mobile;
    @Schema(description = "邮箱")
    protected String email;
    @Schema(description = "激活时间")
    protected Long activeTime;
}
