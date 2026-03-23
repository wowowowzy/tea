package com.example.tea.controller.user;

import cn.hutool.core.lang.Snowflake;
import com.example.tea.entity.pojo.ChatModel.History;
import com.example.tea.entity.pojo.Result;
import com.example.tea.mapper.ChatMapper;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.example.tea.entity.pojo.ChatModel.Prompt.*;

@RestController
@RequestMapping("/api/user/ai")
public class ChatController {
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private ChatMapper chatMapper;

    /**核心ai接口
     *
     * @param message
     * @param sessionId
     * @param image
     * @return
     */
    @PostMapping( "/generateStream")
	public Flux<String> generateStream
            (@RequestParam(value = "message", required = false) String message,
             @RequestParam(value = "sessionId") Long sessionId,
             @RequestPart(value = "image", required = false) MultipartFile image) {
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        chatMapper.add(History.builder()
                .datetime(LocalDateTime.now())
                .content(message)
                .role("user")
                .sessionId(sessionId)
                        .userId(userId)
                .build());

        UserMessage userMessage;
        if (image != null && !image.isEmpty()) {
            image.getContentType();
            MimeType mimeType = MimeTypeUtils.parseMimeType(image.getContentType());
            Media media = null;
            try {
                media = Media.builder()
                        .mimeType(mimeType)
                        .data(image.getBytes())
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            userMessage = UserMessage.builder()
                    .text(message == null || message.isBlank() ? imagePromptWord : message)
                    .media(media)
                    .build();
        } else {
            userMessage = new UserMessage(message);
        }

        List<History> hisories = chatMapper.getHisories(sessionId);
        List<Message> historyMessageList = hisories.stream().map(history -> history.getRole().equals("user") ? new UserMessage(history.getContent())
                : new AssistantMessage(history.getContent())).collect(Collectors.toList());
        StringBuilder[] strBuilder = {new StringBuilder()};
        Flux<String> stream = chatClient.prompt(promptWord)
                .messages(historyMessageList).messages(userMessage).stream().content();
        return stream.doOnNext(s -> strBuilder[0].append(s)).doOnComplete( ()-> chatMapper.add(History.builder()
                .datetime(LocalDateTime.now())
                .content(strBuilder[0].toString())
                .role("assistant")
                .sessionId(sessionId)
                        .userId(userId)
                .build()));
    }

    /**
     * 用userId查询聊天历史记录
     * @param userId
     * @return
     */
    @GetMapping("/getHistories")
    public Result getHistories(@RequestParam(value = "userId") Long userId){
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
        return Result.success(groupBySessionAndSort);
    }

    /**
     * 新建session(新对话)
     * @return
     */
    @GetMapping("/newSession")
    public Result newSession() {
        return Result.success(new Snowflake().nextId());
    }

}
