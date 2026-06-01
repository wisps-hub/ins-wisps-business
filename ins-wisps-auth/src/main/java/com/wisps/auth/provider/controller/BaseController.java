package com.wisps.auth.provider.controller;

import com.wisps.auth.provider.utils.CookieUtil;
import com.wisps.consts.DeviceType;
import consts.Header;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

@Slf4j
public class BaseController {

    public HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    public String getHeader(String name) {
        return getHttpServletRequest().getHeader(name);
    }

    public DeviceType deviceType() {
        return DeviceType.fromCode(getHeader(Header.deviceType));
    }


    public void webLoginSetCookie(Map<String, String> attributes, String cookieDomain, String cookiePath, DeviceType deviceType) {
        if(CollectionUtils.isEmpty(attributes) || DeviceType.WEB != deviceType) {
            return;
        }
        HttpServletResponse response = getHttpServletResponse();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            CookieUtil.setCookie(response, entry.getKey(), entry.getValue(), cookieDomain,
                    cookiePath, true, (int) Header.DEFAULT_TOKEN_EXPIRE_SECONDS);
        }
    }
}