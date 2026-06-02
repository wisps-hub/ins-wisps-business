package com.wisps.auth.provider.config.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedirectRuleConfig {
    private String regExp;
    private String site;
}
