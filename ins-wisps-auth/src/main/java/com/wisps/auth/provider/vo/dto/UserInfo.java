package com.wisps.auth.provider.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    /** 用户id */
    protected String uid;
    /** 组织id */
    protected String oid;
    /** 版本 */
    protected String ver;
    /** 状态 */
    protected Byte status;
    /** 角色 */
    protected Integer role;
    /** 用户在组织内的名称 */
    protected String name;
    /** i18nName */
    protected I18nName i18nName;
    /** 头像 */
    protected String avatarUrl;
    /** 头像Urls */
    protected AvatarUrls avatarUrls;
    /** 国家码 */
    protected String region;
    /** 手机号 */
    protected String mobile;
    /** 邮箱 */
    protected String email;
    /** 激活时间 */
    protected Long activeTime;
}
