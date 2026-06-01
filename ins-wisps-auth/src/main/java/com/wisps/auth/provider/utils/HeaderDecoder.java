package com.wisps.auth.provider.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HeaderDecoder {

    /**
     * 安全解码 Header 值
     * - 中文 %xx 会被正确解码
     * - 英文、数字、符号原样保留
     * - + 不会被当成空格
     *
     * @param headerValue Header 原始值
     * @return 解码后的字符串
     */
    public static String safeDecode(String headerValue) {
        if (StringUtils.isBlank(headerValue)) return "";
        try {
            // 先把 + 替换成 %2B，避免被 URLDecoder 当成空格
            String temp = headerValue.replace("+", "%2B");
            // UTF-8 解码
            return URLDecoder.decode(temp, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            // 出现异常时返回原值
            return headerValue;
        }
    }

}