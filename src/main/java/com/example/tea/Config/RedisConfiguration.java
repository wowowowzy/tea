package com.example.tea.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类（适配jjwt-jackson依赖）
 */
@Slf4j
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建Redis模板对象");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 1. 设置Redis连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 2. 字符串序列化器（key/hashKey）
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 3. JSON序列化器（value/hashValue）：复用jjwt-jackson的Jackson依赖
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 无需手动配置ObjectMapper（jjwt-jackson已默认适配，如需自定义可继续配置）

        // 4. 全局序列化配置
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jacksonSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jacksonSerializer);

        // 5. 初始化模板
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
