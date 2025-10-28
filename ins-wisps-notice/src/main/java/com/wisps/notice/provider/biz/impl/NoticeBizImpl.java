package com.wisps.notice.provider.biz.impl;

import cn.hutool.core.util.RandomUtil;
import com.wisps.cache.client.ICache;
import com.wisps.exception.SystemException;
import com.wisps.notice.api.consts.NoticeConst;
import com.wisps.notice.provider.biz.NoticeBiz;
import com.wisps.notice.provider.consts.NoticeState;
import com.wisps.notice.provider.entity.Notice;
import com.wisps.notice.provider.mapping.dao.NoticeDao;
import com.wisps.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.wisps.exception.BizErrorCode.SEND_NOTICE_DUPLICATED;

@Service
public class NoticeBizImpl implements NoticeBiz {

    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private ICache redisClient;
    @Autowired
    private SmsService smsService;

    @Override
    public Boolean genSendSmsCaptcha(String mobile) {
        //todo wisps 限流
        Boolean access = true;
        if (!access) {
            throw new SystemException(SEND_NOTICE_DUPLICATED);
        }

        // 生成验证码
        String captcha = RandomUtil.randomNumbers(4);
        // 验证码存入Redis
        redisClient.set(NoticeConst.captchaKey(mobile), NoticeConst.EXP_5_MIN, captcha);

        Notice notice = noticeDao.saveCaptcha(mobile, captcha);
        Thread.ofVirtual().start(() -> {
            Boolean sendSms = smsService.sendMsg(notice.getTargetAddress(), notice.getNoticeContent());
            if (sendSms){
                notice.setState(NoticeState.SUCCESS);
                notice.setSendSuccessTime(new Date());
            }else {
                notice.setState(NoticeState.FAILED);
                notice.addExtendInfo("executeResult", "sendSms fail");
            }
            noticeDao.updateById(notice);
        });
        return true;
    }
}