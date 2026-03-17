package com.example.tea.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.tea.entity.dto.Order.OrderDTO;
import com.example.tea.entity.dto.Order.OrderListDTO;
import com.example.tea.entity.dto.Order.OrderPayDTO;
import com.example.tea.entity.pojo.Order.Order;
import com.example.tea.entity.vo.Order.OrderListVO;
import com.example.tea.mapper.OrderMapper;
import com.example.tea.service.OrderService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<OrderListVO> getOrders() {
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        List<OrderDTO> orders = orderMapper.getOrders(userId);
        Map<Long, List<OrderDTO>> listMap = orders.stream()
                .collect(Collectors.groupingBy(orderDTO -> orderDTO.getOrderId()));
        return listMap.entrySet().stream().map(entry -> {
            Long orderId = entry.getKey();
            List<OrderDTO> orderDTOS = entry.getValue();
            LocalDateTime createTime = orderDTOS.get(0).getCreateTime();
            List<OrderListDTO> list = orderDTOS.stream().map(orderDTO -> OrderListDTO.builder()
                    .goodsName(orderDTO.getGoodsName())
                    .goodsIntro(orderDTO.getGoodsIntro())
                    .goodsPrice(orderDTO.getGoodsPrice())
                    .quantity(orderDTO.getQuantity())
                    .goodsImage(orderDTO.getGoodsImage()).build()
            ).toList();
            return OrderListVO.builder()
                    .orderId(orderId)
                    .createTime(createTime)
                    .orderDTOList(list).build();
        }).toList();
    }

    @Override
    public OrderListVO getOrderById(Long orderId) {
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        List<OrderDTO> orders = orderMapper.getOrderById(orderId,userId);
        List<OrderListDTO> list = orders.stream().map(DTO -> {
            OrderListDTO orderListDTO = new OrderListDTO();
            BeanUtils.copyProperties(DTO, orderListDTO);
            return orderListDTO;
        }).toList();
        return OrderListVO.builder()
                .orderId(orderId)
                .createTime(orders.get(0).getCreateTime())
                .orderDTOList(list)
                .build();
    }
}
