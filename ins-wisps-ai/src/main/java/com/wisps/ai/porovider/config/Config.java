package com.wisps.ai.porovider.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring alibaba ai config
 */
@Configuration
public class Config {

    @Value("${spring.ai.dashscope.api-key}")
    private String ak;
    @Value("${spring.ai.dashscope.chat.options.model}")
    private String model;

    @Autowired
    private RedisChatMemoryRepository redisChatMemoryRepository;

    @Bean
    public ChatModel dashScopeChatModel(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(ak).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(model).build())
                .build();
    }

    @Bean
    public ChatClient openAiChatClient(ChatModel openAiChatModel){
        MessageWindowChatMemory windowChatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(redisChatMemoryRepository).maxMessages(10).build();
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(windowChatMemory).build())
                .build();
    }

    @Bean
    public ChatClient dashscopeChatClient(ChatModel dashScopeChatModel){
        return ChatClient.builder(dashScopeChatModel)
                .defaultOptions(ChatOptions.builder().model(model).build()).build();
    }

    @Bean
    public ChatClient ollamaChatClient(ChatModel ollamaChatModel){
        return ChatClient.builder(ollamaChatModel).build();
    }

}