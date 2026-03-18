package com.example.tea.service;

import com.example.tea.entity.dto.Order.OrderPayDTO;
import com.example.tea.entity.vo.Order.OrderListVO;

import java.util.List;

public interface OrderService {
    void pay(List<OrderPayDTO> payDTOList,Long couponId);

   List<OrderListVO> getOrders();

    OrderListVO getOrderById(Long orderId);
}
