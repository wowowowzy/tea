package com.example.tea.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.tea.entity.dto.Order.OrderPayDTO;
import com.example.tea.entity.pojo.Order.Order;
import com.example.tea.mapper.OrderMapper;
import com.example.tea.service.OrderService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    private final Snowflake snowflake = IdUtil.getSnowflake();
    @Override
    public void pay(List<OrderPayDTO> payDTOList) {
        Long orderId = snowflake.nextId();
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        List<Order> orderList = payDTOList.stream().map(DTO -> Order.builder()
                .userId(userId)
                .goodsId(DTO.getGoodsId())
                .orderId(orderId)
                .quantity(DTO.getQuantity())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build()).toList();
        orderMapper.pay(orderList);
    }
}
