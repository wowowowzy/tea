package com.example.tea.controller.user;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.tea.entity.pojo.ChatModel.History;
import com.example.tea.mapper.ChatMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class ChatController {
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private ChatMapper chatMapper;
    private final Snowflake snowflake = IdUtil.getSnowflake();

    @GetMapping( "/ai/generateStream")
	public Flux<String> generateStream
            (@RequestParam(value = "message", defaultValue = "你好") String message,
             @RequestParam(value = "sessionId",required = false) Long sessionId,
             HttpServletResponse response) {
        if(sessionId==null){
            sessionId = snowflake.nextId();
        }
        response.setHeader("sessionId", sessionId.toString());
        response.setContentType("text/event-stream;charset=UTF-8");
        chatMapper.add(History.builder()
                .datetime(LocalDateTime.now())
                .content(message)
                .role("user")
                .sessionId(sessionId)
                .build());
        List<History> hisories = chatMapper.getHisories(sessionId);
        List<Message> MessageList = hisories.stream().map(history -> history.getRole().equals("user") ? new UserMessage(history.getContent())
                : new AssistantMessage(history.getContent())).collect(Collectors.toList());
        StringBuilder[] strBuilder = {new StringBuilder()};
        Flux<String> stream = chatClient.prompt("你是茶叶管家小洋").user(message).messages(MessageList).stream().content();
        Long finalSessionId = sessionId;
        return stream.doOnNext(s -> strBuilder[0].append(s)).doOnComplete( ()-> chatMapper.add(History.builder()
                .datetime(LocalDateTime.now())
                .content(strBuilder[0].toString())
                .role("assistant")
                .sessionId(finalSessionId)
                .build()));
    }

}
