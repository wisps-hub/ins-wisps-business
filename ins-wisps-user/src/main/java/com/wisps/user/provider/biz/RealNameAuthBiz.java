package com.wisps.user.provider.biz;

public interface RealNameAuthBiz {
    /**
     * 实名认证
     */
    boolean checkRealName(String realName, String idCard);
}
