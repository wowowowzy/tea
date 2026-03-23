package com.example.tea.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
public class RabbitMQDelayConfig {
    public static final String COUPON_DELAY_EXCHANGE = "coupon.delay.exchange";
    public static final String COUPON_DELAY_QUEUE = "coupon.delay.queue";
    public static final String COUPON_DELAY_ROUTING_KEY = "coupon.delay.routing.key";

    /**
     * 简化版延迟交换机定义（核心参数保留，冗余参数省略）
     */
    @Bean
    public CustomExchange couponDelayExchange() {
        // 简化Map创建：用静态内部类/单例Map（Java9+也可用Map.of()）
        Map<String, Object> args = Map.of("x-delayed-type", "direct");

        // 核心参数：名称、类型(x-delayed-message)、持久化(true)，自动删除用默认值(false)
        return new CustomExchange(COUPON_DELAY_EXCHANGE, "x-delayed-message", true, false,args);
    }

    // 队列、绑定的定义不变（省略，保持之前的写法）
    @Bean
    public Queue couponDelayQueue() {
        return QueueBuilder.durable(COUPON_DELAY_QUEUE).build();
    }

    @Bean
    public Binding couponDelayBinding(Queue couponDelayQueue, CustomExchange couponDelayExchange) {
        return BindingBuilder.bind(couponDelayQueue)
                .to(couponDelayExchange)
                .with(COUPON_DELAY_ROUTING_KEY)
                .noargs();
    }
}
