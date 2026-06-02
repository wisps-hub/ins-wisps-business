package com.wisps.auth.provider.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import java.time.Duration;
import java.util.Objects;

/**
 * cookie工具类
 **/
@Slf4j
public class CookieUtil {

    public static final String WISPS_HEADER_AUTHORIZATION = "Wisps";

    public static final String WISPS_COOKIE_AUTHORIZATION = "wisps-token";

    public static final String WISPS_PARAM_AUTHORIZATION = "token";

    /**
     * 设置cookie
     * @param response
     * @param key
     * @param value
     * @param domain
     * @param path
     * @param httpOnly
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response, String key, String value, String domain, String path, boolean httpOnly, int maxAge) {

        ResponseCookie cookie = ResponseCookie.from(key, value)
                .httpOnly(httpOnly)
                .secure(true)
                .sameSite("None")  // 也可以设置为Strict或Lax
                .maxAge(Duration.ofSeconds(maxAge))
                .path(path)
                .domain(domain)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    /**
     * 删除cookie
     * @param response
     * @param key
     * @param domain
     * @param path
     */
    public static void deleteCookie(HttpServletResponse response, String key, String domain, String path) {
        ResponseCookie cookie = ResponseCookie.from(key, null)
                .maxAge(0)
                .path(path)
                .domain(domain)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static String getToken(HttpServletRequest request) {
        String token = null;
        // 从 header 中获取
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(authorization)) {
            String[] split = authorization.split(" ", 2);
            if (split.length == 2) {
                token = split[1];
            }
            else {
                token = split[0].startsWith(WISPS_HEADER_AUTHORIZATION) ? null : split[0];
            }
        }
        // 从 cookie 中获取
        if (StringUtils.isEmpty(token)) {
            Cookie cookie = getCookie(request, WISPS_COOKIE_AUTHORIZATION);
            if (Objects.nonNull(cookie)) {
                token = cookie.getValue();
            }
        }
        // 从参数中获取
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(WISPS_PARAM_AUTHORIZATION);
        }
        return token;
    }

    private static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookieName.equals(cookie.getName())){
                return cookie;
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        String token = null;
        // 从 cookie 中获取
        Cookie cookie = getCookie(request, key);
        if (Objects.nonNull(cookie)) {
            token = cookie.getValue();
        }
        return token;
    }
}
