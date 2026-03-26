package com.example.tea.rabbitmq.consumer;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.tea.config.RabbitMQseckillConfig;
import com.example.tea.entity.dto.Goods.SeckillGoodsMessageDTO;
import com.example.tea.entity.dto.Order.OrderPayDTO;
import com.example.tea.entity.pojo.Order.Order;
import com.example.tea.entity.pojo.Order.OrderDetail;
import com.example.tea.mapper.GoodsMapper;
import com.example.tea.mapper.OrderMapper;
import com.example.tea.service.OrderService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 秒杀消息消费者
 * 从MQ拿消息 → 真正创建订单
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SeckillConsumer {
    private final GoodsMapper goodsMapper;
    private final Snowflake snowflake = IdUtil.getSnowflake();
    private final OrderMapper orderMapper;

    /**
     * 监听秒杀队列 → 异步消费
     */
    @RabbitListener(queues = RabbitMQseckillConfig.SECKILL_QUEUE)
    @Transactional(rollbackFor = Exception.class)
    public void handleSeckillMessage(SeckillGoodsMessageDTO message) {
        Long goodsId = message.getGoodsId();
        Long userId = message.getUserId();
        Long orderId = snowflake.nextId();
        Order order = Order.builder()
                .userId(userId)
                .goodsId(Math.toIntExact(goodsId))
                .orderId(orderId)
                .quantity(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(0)
                .build();
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        orderMapper.pay(orderList);
        BigDecimal goodsPrice = goodsMapper.findGoodById(goodsId).getGoodsPrice();
        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(String.valueOf(orderId))
                .userId(userId)
                .totalPrice(goodsPrice)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        goodsMapper.subSeckillStock(goodsId);
        goodsMapper.subGoodsStock(goodsId);
        orderMapper.insertDetail(orderDetail);
    }
}
