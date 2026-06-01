package com.wisps.auth.provider.vo.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SingleUserMobileLoginDto extends MobileLoginDto{
    private String uid;
}
