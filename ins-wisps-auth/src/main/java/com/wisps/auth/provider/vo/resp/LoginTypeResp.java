package com.wisps.auth.provider.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTypeResp {
    @Schema(description = "登录方式列表")
    private List<Integer> loginTypeList;
}
