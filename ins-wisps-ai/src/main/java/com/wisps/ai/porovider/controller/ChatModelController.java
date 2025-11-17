package com.wisps.ai.porovider.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

@RestController
@RequestMapping("/model/chat")
public class ChatModelController {

    @Resource(name = "openAiChatModel")
    private ChatModel openAiChatModel;

    @Resource(name = "dashScopeChatModel")
    private ChatModel dashScopeChatModel;

    @Resource(name = "ollamaChatModel")
    private ChatModel ollamaChatModel;

    private ChatModel getChatModel(String model){
        return switch (model) {
            case "openAi" -> openAiChatModel;
            case "dashscope" -> dashScopeChatModel;
            case "ollama" -> ollamaChatModel;
            default -> dashScopeChatModel;
        };
    }

    @GetMapping("/once")
    public String onceChat(@RequestParam(name = "msg", defaultValue = "你是？")String msg,
                           @RequestParam("model")String model){
        return getChatModel(model).call(msg);
    }

    @GetMapping("/stream")
    public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是？")String msg,
                                   @RequestParam("model")String model){
        return getChatModel(model).stream(msg);
    }
}