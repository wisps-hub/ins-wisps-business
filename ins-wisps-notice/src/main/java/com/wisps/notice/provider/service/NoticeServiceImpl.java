package com.wisps.notice.provider.service;

import com.wisps.notice.api.service.NoticeService;
import com.wisps.notice.provider.biz.NoticeBiz;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(version = "1.0.0")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeBiz noticeBiz;

    @Override
    public Boolean genSendSmsCaptcha(String mobile) {
        return noticeBiz.genSendSmsCaptcha(mobile);
    }

}