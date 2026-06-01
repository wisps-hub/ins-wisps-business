package com.wisps.auth.provider.vo.resp;

import lombok.Data;

import java.util.Map;

@Data
public class I18nNameVo {
    // en | zh | other
    private Map<String, String> names;
    // 默认 en
    private String defaultLang;
}
