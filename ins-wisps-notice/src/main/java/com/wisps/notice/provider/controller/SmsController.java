package com.wisps.notice.provider.controller;

import com.wisps.notice.provider.biz.NoticeBiz;
import com.wisps.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {

    @Autowired
    private NoticeBiz noticeBiz;

    @GetMapping("/sendSmsCaptcha/{mobile}")
    public Result<Boolean> genSendSmsCaptcha(@PathVariable("mobile") String mobile){
        return Result.success(noticeBiz.genSendSmsCaptcha(mobile));
    }
}
