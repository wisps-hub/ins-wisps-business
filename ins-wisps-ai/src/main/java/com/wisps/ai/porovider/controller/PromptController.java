package com.wisps.ai.porovider.controller;

import com.wisps.ai.porovider.records.Student;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/prompt/chat")
public class PromptController {

    @Resource
    private ChatClient openAiChatClient;
    @Resource
    private ChatModel openAiChatModel;
    @Value("classpath:/prompts/prompt.txt")
    private org.springframework.core.io.Resource promptTemp;


    @GetMapping("/flux")
    public Flux<String> flux(@RequestParam(name = "msg")String msg){
        return openAiChatClient.prompt()
                .system("你是一个程序员，只回答编程相关问题，其他问题回复：我只能回答编程相关问题，其它无可奉告")
                .user(msg)
                .stream().content();
    }

    @GetMapping("/flux2")
    public Flux<ChatResponse> flux2(@RequestParam(name = "msg")String msg){
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事助手， 每个故事控制在200字内");
        UserMessage userMessage = new UserMessage(msg);
        Prompt prompt = new Prompt(userMessage, systemMessage);
        return openAiChatModel.stream(prompt);
    }

    @GetMapping("/flux3")
    public Flux<String> flux3(@RequestParam(name = "msg")String msg){
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事助手， 每个故事控制在200字内并且以html格式返回");
        UserMessage userMessage = new UserMessage(msg);
        Prompt prompt = new Prompt(userMessage, systemMessage);
        return openAiChatModel.stream(prompt).mapNotNull(chatResponse -> chatResponse.getResults().get(0).getOutput().getText());
    }

    @GetMapping("/flux4")
    public String flux4(@RequestParam(name = "msg")String msg){
        AssistantMessage assistantMessage = openAiChatClient.prompt()
                .user(msg)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();
        return assistantMessage.getText();
    }

    @GetMapping("/flux5")
        public Flux<String> flux5(@RequestParam(name = "topic")String topic,
                              @RequestParam(name = "out_format")String outFormat,
                              @RequestParam(name = "word_count")String wordCount){
        PromptTemplate promptTemplate =
                new PromptTemplate("讲一个关于{topic}的故事，并以{outFormat}格式输出，字数控制在{wordCount}左右");
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "outFormat", outFormat,
                "wordCount", wordCount));
        return openAiChatClient.prompt(prompt).stream().content();
    }

    @GetMapping("/flux6")
    public Flux<String> flux6(@RequestParam(name = "topic")String topic,
                              @RequestParam(name = "out_format")String outFormat,
                              @RequestParam(name = "word_count")String wordCount){
        PromptTemplate promptTemplate = new PromptTemplate(promptTemp);
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "outFormat", outFormat,
                "wordCount", wordCount));
        return openAiChatClient.prompt(prompt).stream().content();
    }

    @GetMapping("/flux7")
    public Flux<String> flux7(@RequestParam(name = "system_topic")String system_topic,
                              @RequestParam(name = "user_topic")String user_topic){
        SystemPromptTemplate systemPromptTemplate =
                new SystemPromptTemplate("你是一个{system_topic}助手，只回答{system_topic}相关问题，并且以html格式输出");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("system_topic", system_topic));

        PromptTemplate userPromptTemplate =
                new PromptTemplate("解释一下{user_topic}");
        Message userMessage = userPromptTemplate.createMessage(Map.of("user_topic", user_topic));

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return openAiChatClient.prompt(prompt).stream().content();
    }

    @GetMapping("/flux8")
    public Student flux8(@RequestParam(name = "name")String name,
                         @RequestParam(name = "email")String email){
        return openAiChatClient.prompt().user(promptUserSpec ->
                promptUserSpec.text("我叫{name}, 土木工程系，邮箱{email}, 学好10001")
                .params(Map.of("name", name, "email", email))).call().entity(Student.class);
    }
}