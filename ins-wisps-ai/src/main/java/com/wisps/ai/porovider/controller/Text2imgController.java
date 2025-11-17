package com.wisps.ai.porovider.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/t2i/chat")
public class Text2imgController {

    private static final String IMG_MODEL = "wan2.5-t2i-preview";

    @Resource
    private ImageModel dashScopeImageModel;

    @GetMapping("/once")
    public String onceChat(@RequestParam(name = "prompt", defaultValue = "萝卜兔")String prompt){
        return dashScopeImageModel.call(
                new ImagePrompt(prompt, DashScopeImageOptions.builder().withModel(IMG_MODEL).build()))
                .getResult().getOutput().getUrl();
    }

}