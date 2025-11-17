package com.wisps.ai.porovider.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client/chat")
public class ChatClientController {

    @Resource
    private ChatClient openAiChatClient;

    @Resource
    private ChatClient dashscopeChatClient;

    @Resource
    private ChatClient ollamaChatClient;

    private ChatClient getChatModel(String model){
        return switch (model) {
            case "openAi" -> openAiChatClient;
            case "dashscope" -> dashscopeChatClient;
            case "ollama" -> ollamaChatClient;
            default -> dashscopeChatClient;
        };
    }

    @GetMapping("/once")
    public String onceChat(@RequestParam(name = "msg", defaultValue = "你是？")String msg,
                           @RequestParam("model")String model){
        return getChatModel(model).prompt().user(msg).call().content();
    }

    @GetMapping("/stream")
    public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是？")String msg,
                                   @RequestParam("model")String model){
        return getChatModel(model).prompt().user(msg).stream().content();
    }
}