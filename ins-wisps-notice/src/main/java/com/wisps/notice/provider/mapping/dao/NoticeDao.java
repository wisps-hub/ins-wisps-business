package com.wisps.notice.provider.mapping.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisps.notice.provider.entity.Notice;

public interface NoticeDao extends IService<Notice> {
    Notice saveCaptcha(String telephone, String captcha);
}
