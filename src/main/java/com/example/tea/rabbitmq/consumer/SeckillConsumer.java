package com.example.tea.rabbitmq.consumer;

import com.example.tea.config.RabbitMQseckillConfig;
import com.example.tea.entity.dto.Goods.SeckillGoodsMessageDTO;
import com.example.tea.entity.dto.Order.OrderPayDTO;
import com.example.tea.service.OrderService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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

    // 你项目里的 订单Service（改成你自己的就行）
    private final OrderService orderService;

    /**
     * 监听秒杀队列 → 异步消费
     */
    @RabbitListener(queues = RabbitMQseckillConfig.SECKILL_QUEUE)
    public void seckillConsumer(SeckillGoodsMessageDTO message) {
        try {
            Long goodsId = message.getGoodsId();
            Long userId = message.getUserId();
            ThreadLocalUserIdUtil.setCurrentId(userId);
            log.info("【秒杀消费】开始创建订单 userId:{} goodsId:{}", userId, goodsId);
            List<OrderPayDTO> payDTOList = new ArrayList<>();
            payDTOList.add(OrderPayDTO.builder()
                    .goodsId(Math.toIntExact(goodsId))
                    .quantity(1)
                    .build());
            orderService.pay(payDTOList,null);
            log.info("【秒杀消费】订单创建成功 userId:{} goodsId:{}", userId, goodsId);

        } catch (Exception e) {
            log.error("【秒杀消费】异常：", e);
        }
    }
}
