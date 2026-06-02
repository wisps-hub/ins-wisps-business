package com.wisps.auth.provider.config;

import com.wisps.auth.provider.exception.AuthErrorCode;
import com.wisps.cache.consts.WispsCacheConst;
import com.wisps.exception.BizException;

import java.util.concurrent.TimeUnit;

public interface CacheConfig {
    String delimiter= WispsCacheConst.SEPARATOR;
    String empty="~!@#$%";

    String getPrefix();
    long getTtl();
    TimeUnit getTimeUnit();

    default String genKey(String... keys){
        if(keys.length==0) {
            throw new BizException(AuthErrorCode.INVALID_PARAMETER);
        }
        StringBuilder sb =  new StringBuilder(getPrefix());
        for(String k:keys){
            sb.append(delimiter).append(k);
        }
        return sb.toString();
    }
}
