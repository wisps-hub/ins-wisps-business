package com.wisps.user.provider.biz.impl;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import com.wisps.user.provider.biz.RealNameAuthBiz;
import com.wisps.user.provider.config.AuthProperties;
import com.wisps.user.provider.consts.Consts;
import com.wisps.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class RealNameAuthBizImpl implements RealNameAuthBiz {

    @Autowired
    private AuthProperties authProperties;

    @Override
    public boolean checkRealName(String realName, String idCard) {
        String appcode = authProperties.getAppcode();
        String host = authProperties.getHost();
        String path = authProperties.getPath();
        Map<String, String> headers = Maps.newHashMapWithExpectedSize(2);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = Maps.newHashMapWithExpectedSize(2);
        Map<String, String> bodys = Maps.newHashMapWithExpectedSize(2);
        bodys.put("id_number", idCard);
        bodys.put("name", realName);
        try {
            var response = HttpUtils.doPost(host, path, HttpMethod.POST.name(), headers, querys, bodys);
            Map<String, Object> resultMap = JSON.parseObject(EntityUtils.toString(response.getEntity()), Map.class);
            log.info("auth result : " + resultMap);
            return (Integer)resultMap.get(Consts.STATE) == 1;
        } catch (Exception e) {
            log.error("checkAuth error realName=" + realName, e);
        }
        return false;
    }
}
