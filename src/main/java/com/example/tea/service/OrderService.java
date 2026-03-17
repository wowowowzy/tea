package com.example.tea.service;

import com.example.tea.entity.dto.Order.OrderPayDTO;

import java.util.List;

public interface OrderService {
    void pay(List<OrderPayDTO> payDTOList);
}
