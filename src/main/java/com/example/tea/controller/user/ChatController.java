package com.example.tea.controller.user;

import com.example.tea.entity.pojo.ChatModel.History;
import com.example.tea.entity.pojo.ChatModel.Prompt;
import com.example.tea.mapper.ChatMapper;
import com.example.tea.utils.ThreadLocalUserIdUtil;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class ChatController {
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private ChatMapper chatMapper;

    /*
    * 核心ai接口
    * */
    @GetMapping( "/ai/generateStream")
	public Flux<String> generateStream
            (@RequestParam(value = "message", defaultValue = "你好") String message,
             @RequestParam(value = "sessionId") Long sessionId) {
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        chatMapper.add(History.builder()
                .datetime(LocalDateTime.now())
                .content(message)
                .role("user")
                .sessionId(sessionId)
                        .userId(userId)
                .build());
        List<History> hisories = chatMapper.getHisories(sessionId);
        List<Message> MessageList = hisories.stream().map(history -> history.getRole().equals("user") ? new UserMessage(history.getContent())
                : new AssistantMessage(history.getContent())).collect(Collectors.toList());
        StringBuilder[] strBuilder = {new StringBuilder()};
        Flux<String> stream = chatClient.prompt(new Prompt().getPrompt())
                .user(message).messages(MessageList).stream().content();
        Long finalSessionId = sessionId;
        return stream.doOnNext(s -> strBuilder[0].append(s)).doOnComplete( ()-> chatMapper.add(History.builder()
                .datetime(LocalDateTime.now())
                .content(strBuilder[0].toString())
                .role("assistant")
                .sessionId(finalSessionId)
                        .userId(userId)
                .build()));
    }
    /*
    * 根据用userId查询聊天历史记录*/
    @GetMapping("/ai/getHistories")
    public Map<Long,List<History>> getHistories(@RequestParam(value = "userId") Long userId){
        List<History> hisories = chatMapper.getHisoriesByUserId(userId);
        Map<Long, List<History>> groupBySessionAndSort = hisories.stream()
                .collect(Collectors.groupingBy(
                        History::getSessionId, // 第一步：按sessionId分组
                        // 第二步：对每个分组的列表按datetime排序（升序：从早到晚）
                        Collectors.collectingAndThen(
                                Collectors.toList(), // 先收集为列表
                                list -> list.stream()
                                        .sorted(Comparator.comparing(History::getDatetime))
                                        .toList()
                        )
                ));
        return groupBySessionAndSort;
    }

}
