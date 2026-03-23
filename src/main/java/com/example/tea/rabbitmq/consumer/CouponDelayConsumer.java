package com.example.tea.rabbitmq.consumer;

import com.example.tea.config.RabbitMQDelayConfig;
import com.example.tea.entity.pojo.Coupon.Coupon;
import com.example.tea.mapper.CouponMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 优惠券延迟消息消费者
 */
@Component
public class CouponDelayConsumer {
    @Autowired
    private CouponMapper couponMapper;

    /**
     * 监听优惠券延迟队列
     * @param message 接收到的消息体
     */
    @RabbitListener(queues = RabbitMQDelayConfig.COUPON_DELAY_QUEUE) // 指定监听的队列
    public void receiveDelayCouponMessage(String message) {
        try {
            // 1. 解析消息（示例：提取优惠券ID）
            Long couponId = Long.parseLong(message.replace("CouponExpire_", ""));

            // 2. 执行业务逻辑（核心：处理优惠券过期，比如修改状态、通知用户等）
            Coupon coupon = couponMapper.getExpiredCouponById(couponId);
            if(coupon!=null){
                couponMapper.updateAnExpiredCoupon(coupon);
                coupon.setStatus(Coupon.STATUS_EXPIRED);
            }
            System.out.println("📌 处理优惠券过期逻辑：优惠券ID=" + couponId + "，状态改为【已过期】");

            System.out.println("✅ 延迟优惠券消息消费成功：优惠券ID=" + couponId + "，消息内容=" + message);
        } catch (Exception e) {
            System.err.println("❌ 延迟优惠券消息消费失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
