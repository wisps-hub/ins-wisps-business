package com.wisps.auth.provider.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sso_account_mfa")
public class SsoAccountMFaEntity {
    @TableId(type = IdType.INPUT)
    private String id;

    private String accountId;

    private Integer mfaMethod;

    private Integer methodStatus;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createtime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifytime;

    public static class Column{
        public static final String id = "id";
        public static final String accountId = "account_id";
        public static final String oid = "mfa_method";
        public static final String uid = "method_status";
        public static final String createtime = "createtime";
        public static final String modifytime = "modifytime";
    }
}
