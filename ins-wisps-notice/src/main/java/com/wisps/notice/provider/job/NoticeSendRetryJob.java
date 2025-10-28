package com.wisps.notice.provider.job;

import com.wisps.notice.provider.biz.impl.NoticeBizImpl;
import com.wisps.sms.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息通知任务
 * <p>
 * 已废弃，失败不自动重试。靠用户手动重试
 * </p>
 */
@Component
public class NoticeSendRetryJob {

    @Autowired
    private NoticeBizImpl noticeService;

    @Autowired
    private SmsService smsService;

    private static final int PAGE_SIZE = 100;

    private static final Logger LOG = LoggerFactory.getLogger(NoticeSendRetryJob.class);

    //todo wisps 扫表重试发送验证码
}
