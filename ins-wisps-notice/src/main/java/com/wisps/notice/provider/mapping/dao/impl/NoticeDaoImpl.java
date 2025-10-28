package com.wisps.notice.provider.mapping.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.exception.BizException;
import com.wisps.notice.api.consts.NoticeConst;
import com.wisps.notice.provider.consts.NoticeState;
import com.wisps.notice.provider.consts.NoticeType;
import com.wisps.notice.provider.entity.Notice;
import com.wisps.notice.provider.mapping.dao.NoticeDao;
import com.wisps.notice.provider.mapping.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import static com.wisps.exception.BizErrorCode.NOTICE_SAVE_FAILED;

@Service
public class NoticeDaoImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeDao {

    public Notice saveCaptcha(String telephone, String captcha) {
        Notice notice = Notice.builder()
                .noticeTitle(NoticeConst.SMS_NOTICE_TITLE)
                .noticeContent(captcha)
                .noticeType(NoticeType.SMS)
                .targetAddress(telephone)
                .state(NoticeState.INIT)
                .build();
        if (!this.save(notice)) {
            throw new BizException(NOTICE_SAVE_FAILED);
        }
        return notice;
    }

}