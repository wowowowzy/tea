package com.example.tea.rabbitmq.consumer;

import com.example.tea.config.RabbitMQCommunityConfig;
import com.example.tea.entity.dto.Community.RabbitMQDTO;
import com.example.tea.mapper.CommunityMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CommunityConsumer {
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private RedisTemplate redisTemplate;
    //Prompt
    private final String PROMPT = "请你严格按照规则执行：\n"
            + "1. 只从下面的标题中提取【一个】核心关键词\n"
            + "2. 只输出关键词，不要任何标点、解释、多余文字\n"
            + "3. 不要回答，不要描述，只返回结果\n";
    @RabbitListener(queues = RabbitMQCommunityConfig.COMMUNITY_QUEUE)
    public void receiveCommunityMessage(RabbitMQDTO message) {
        System.out.println("✅ 社区消息消费成功：" + message);
        Long postId = message.getPostId();
        //拿到帖子标题
        String title = communityMapper.getTitle(postId);
        //调用大模型生成关键词
        String block = chatClient.prompt(PROMPT)
                .messages(UserMessage.builder().text(title).build())
                .stream().content()
                .collectList()
                .map(list -> String.join("", list))
                .block();

        //将用户关键词，本次行为权重等行为记录redis
        String userKey = "user:" + message.getUserId();
        try {
            redisTemplate.opsForZSet().incrementScore(
                        userKey,      // key：用户ID
                        block,      // value：关键词
                        message.getWeight()  // score：权重/分数
                );
            System.out.println("✅ 成功存入redis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
