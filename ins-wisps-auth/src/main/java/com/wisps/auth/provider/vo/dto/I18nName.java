package com.wisps.auth.provider.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class I18nName {
    // en | zh | other
    private Map<String, String> names;
    // 默认 en
    private String defaultLang;
}
