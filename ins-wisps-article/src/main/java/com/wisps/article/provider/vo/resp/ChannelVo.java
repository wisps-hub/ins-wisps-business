package com.wisps.article.provider.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChannelVo implements Serializable {
    private Long id;
    private String channelName;
    private Integer levelCode;
}
