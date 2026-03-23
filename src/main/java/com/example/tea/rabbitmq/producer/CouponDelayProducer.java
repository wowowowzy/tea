package com.example.tea.rabbitmq.producer;

import com.example.tea.config.RabbitMQDelayConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 优惠券延迟消息生产者
 */
@Component
public class CouponDelayProducer {

    // 注入RabbitMQ模板（Spring Boot自动配置）
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送延迟优惠券消息
     * @param couponId 优惠券ID（业务参数）
     * @param delayTime 延迟时间（单位：毫秒）
     */
    public void sendDelayCouponMessage(Long couponId, long delayTime) {
        try {
            // 1. 构造消息体（这里用优惠券ID示例，可替换为JSON字符串/自定义对象）
            String message = "CouponExpire_" + couponId;

            // 2. 发送延迟消息：通过MessagePostProcessor设置延迟时间
            rabbitTemplate.convertAndSend(
                    RabbitMQDelayConfig.COUPON_DELAY_EXCHANGE,  // 延迟交换机
                    RabbitMQDelayConfig.COUPON_DELAY_ROUTING_KEY, // 路由键
                    message,                                      // 消息体
                    // 核心：设置延迟时间（x-delay参数）
                    messagePostProcessor -> {
                        messagePostProcessor.getMessageProperties()
                                .setHeader("x-delay", delayTime); // 延迟时间（毫秒）
                        return messagePostProcessor;
                    }
            );

            System.out.println("✅ 延迟优惠券消息发送成功：优惠券ID=" + couponId + "，延迟时间=" + delayTime + "ms");
        } catch (Exception e) {
            System.err.println("❌ 延迟优惠券消息发送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
