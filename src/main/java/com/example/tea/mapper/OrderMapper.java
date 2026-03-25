package com.example.tea.mapper;

import com.example.tea.entity.dto.Order.OrderDTO;
import com.example.tea.entity.pojo.Order.Order;
import com.example.tea.entity.pojo.Order.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {
    void pay(List<Order> orderList);

    List<OrderDTO> getOrders(Long userId);

    List<OrderDTO> getOrderById(Long orderId, Long userId);

    void insertDetail(OrderDetail orderDetail);

    List<OrderDTO> getOrdersAdmin();

    @Update("update `order` set status = 1 where id = #{orderId}")
    void approval(Long orderId);
}
