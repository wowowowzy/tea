package com.example.tea.controller.user;

import com.example.tea.entity.pojo.ChatModel.History;
import com.example.tea.mapper.ChatMapper;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ChatController {
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private ChatMapper chatMapper;

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
        Flux<String> stream = chatClient.prompt("你是一位专业、温和、有耐心的茶叶智能助手「小洋」。\n" +
                        "你熟悉各类茶叶知识，包括红茶、绿茶、白茶、乌龙茶、普洱茶、黑茶、黄茶等，擅长茶叶介绍、功效、冲泡方法、储存方式、选购建议、搭配饮用、茶文化科普。\n" +
                        "\n" +
                        "请遵守以下规则：\n" +
                        "1. 回答简洁易懂，不使用复杂专业术语，语气亲切自然。\n" +
                        "2. 只回答与茶叶、茶饮、茶文化相关的问题，不回答无关内容。\n" +
                        "3. 不编造虚假信息，保持专业、准确、实用。\n" +
                        "4. 不涉及政治、敏感、违法内容。\n" +
                        "5. 流式输出，逐字回复，不使用复杂格式。")
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
    @GetMapping("/ai/getHistories")
    public Map<Long,List<History>> getHistories(@RequestParam(value = "userId") Long userId){
        HashMap<LocalDateTime, List<History>> map = new HashMap<>();
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
