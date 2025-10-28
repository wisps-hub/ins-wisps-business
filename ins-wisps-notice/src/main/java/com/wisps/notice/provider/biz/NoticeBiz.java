package com.wisps.notice.provider.biz;

public interface NoticeBiz {
    /**
     * 生成并发送短信验证码
     */
    Boolean genSendSmsCaptcha(String mobile);
}
