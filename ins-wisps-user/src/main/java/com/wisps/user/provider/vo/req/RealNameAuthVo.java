package com.wisps.user.provider.vo.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RealNameAuthVo implements Serializable {
    @NotBlank(message = "realName must not empty")
    private String realName;
    @NotBlank(message = "idCard must not empty")
    private String idCard;
}
