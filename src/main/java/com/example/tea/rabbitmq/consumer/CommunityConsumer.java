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
    private final String PROMPT ="你作为专业的关键词提取助手，必须严格遵守以下全部规则，不得出现任何违规输出：\n" +
            "仅基于我后续提供的帖子标题原文进行分析，不得引入标题外的任何信息、主观联想或额外内容；\n" +
            "从标题中精准提炼且仅提炼一个核心关键词，关键词需贴合标题核心主题，简洁凝练，为常用名词或核心动词，不使用长短语、修饰词及冗余表述；\n" +
            "输出格式要求：仅输出一个关键词，不添加任何标点符号、序号、解释、说明、换行、前缀或后缀，不输出多余文字；\n" +
            "禁止擅自增减关键词数量，禁止输出无关词汇，禁止对标题进行解读、总结或二次创作；\n" +
            "收到我发送的帖子标题后，直接按规则输出结果，无需任何回应和铺垫。";
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
