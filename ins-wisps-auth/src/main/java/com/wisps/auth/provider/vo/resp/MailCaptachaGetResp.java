package com.wisps.auth.provider.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "获取邮箱验证码结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailCaptachaGetResp implements Serializable {

    @Schema(description = "短信失效时间(单位/秒)")
    private Long verifyExpireTime;
}
