package com.wisps.auth.provider.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarUrls {
    private String original;
    private String hd500;
    private String hd150;
}
