package com.example.tea.service.impl;

import com.example.tea.config.RabbitMQseckillConfig;
import com.example.tea.entity.dto.Goods.SeckillGoodsMessageDTO;
import com.example.tea.service.SeckillService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {

    private final StringRedisTemplate redisTemplate;
    private final RabbitTemplate rabbitTemplate;

    // Redis key 前缀
    private static final String STOCK_PREFIX = "seckill:stock:";
    private static final String USER_PREFIX = "seckill:user:";

    /**
     * 秒杀核心方法
     */
    public String seckill(Long goodsId) {
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        // 1. 拼接key
        String stockKey = STOCK_PREFIX + goodsId;
        String userKey = USER_PREFIX + goodsId + ":" + userId;

        // 2. 判断用户是否已经抢过（Redis判断）
        if (Boolean.TRUE.equals(redisTemplate.hasKey(userKey))) {
            return "您已经抢购过此商品";
        }

        // 3. 原子扣库存（最核心！防超卖）
        Long remainStock = redisTemplate.opsForValue().decrement(stockKey);

        // 4. 库存不足
        if (remainStock < 0) {
            // 把多减的 1 加回去
            redisTemplate.opsForValue().increment(stockKey);
            return "已抢完";
        }

        // 5. 标记用户已抢（24小时过期）
        redisTemplate.opsForValue().set(userKey, "1", 24, TimeUnit.HOURS);

        // 6. 发送消息到 MQ → 异步下单
        SeckillGoodsMessageDTO message = new SeckillGoodsMessageDTO();
        message.setGoodsId(goodsId);
        message.setUserId(userId);

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQseckillConfig.SECKILL_EXCHANGE,
                    RabbitMQseckillConfig.SECKILL_ROUTING_KEY,
                    message
            );
        } catch (Exception e) {
            // MQ发送失败 → 库存回滚
            redisTemplate.opsForValue().increment(stockKey);
            redisTemplate.delete(userKey);
            log.error("MQ发送失败，库存回滚");
            return "系统繁忙，请重试";
        }
        return "抢购成功，已排队生成订单";
    }

    /**
     * 初始化库存到 Redis（秒杀前调用）
     */
    public String initStock(Long goodsId, int stock) {
            String stockKey = STOCK_PREFIX + goodsId;
            redisTemplate.opsForValue().set(stockKey, String.valueOf(stock));
            log.info("商品{} 初始化库存：{}", goodsId, stock);
            return "初始化成功";
    }
}
