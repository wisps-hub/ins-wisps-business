package com.wisps.auth.provider.utils;

import consts.Header;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IpUtil {
    private static final String UNKNOWN = "unknown";

    public static String extractClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader(Header.HEADER_CLIENT_IP);
        if (isValidIp(clientIp)) {
            if(log.isDebugEnabled()){
                log.debug("[IP Extractor] Found IP in CLIENT-IP: {}", clientIp);
            }
            return clientIp;
        }

        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (isValidIp(xForwardedFor)) {
            if(log.isDebugEnabled()){
                log.debug("[IP Extractor] Found IP in X-Forwarded-For: {}", xForwardedFor);
            }
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (isValidIp(xRealIp)) {
            if(log.isDebugEnabled()) {
                log.debug("[IP Extractor] Found IP in X-Real-IP: {}", xRealIp);
            }
            return xRealIp;
        }

        String forwarded = request.getHeader("Forwarded");
        if (forwarded != null && forwarded.contains("for=")) {
            String ip = forwarded.split(";")[0].split("=")[1].replaceAll("\"", "").trim();
            if (isValidIp(ip)) {
                if(log.isDebugEnabled()) {
                    log.debug("[IP Extractor] Found IP in Forwarded: {}", ip);
                }
                return ip;
            }
        }
        String ip = request.getRemoteAddr();
        return ip;
    }

    private static boolean isValidIp(String ip) {
        return ip != null && !ip.isEmpty() && !UNKNOWN.equalsIgnoreCase(ip);
    }
}
