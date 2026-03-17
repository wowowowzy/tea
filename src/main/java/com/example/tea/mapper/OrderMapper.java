package com.example.tea.mapper;

import com.example.tea.entity.pojo.Order.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void pay(List<Order> orderList);
}
