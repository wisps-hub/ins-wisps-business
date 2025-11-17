package com.wisps.ai.porovider.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.function.Consumer;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/memory/chat")
public class MemoryChatController {

    @Resource
    private ChatClient openAiChatClient;

    @GetMapping("/once")
    public String onceChat(@RequestParam(name = "msg", defaultValue = "你是？")String msg,
                           @RequestParam("uid")String uid){
        return openAiChatClient.prompt(msg)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID , uid))
                .call().content();
    }

}