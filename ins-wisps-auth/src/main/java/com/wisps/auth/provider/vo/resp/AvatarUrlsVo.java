package com.wisps.auth.provider.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvatarUrlsVo implements Serializable {
    private String original;
    private String hd500;
    private String hd150;
}
