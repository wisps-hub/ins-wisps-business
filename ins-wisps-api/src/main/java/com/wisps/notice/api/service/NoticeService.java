package com.wisps.notice.api.service;

public interface NoticeService {
    /**
     * 生成并发送短信验证码
     */
    Boolean genSendSmsCaptcha(String mobile);
}
