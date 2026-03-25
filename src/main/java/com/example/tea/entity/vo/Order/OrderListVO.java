package com.example.tea.entity.vo.Order;

import com.example.tea.entity.dto.Order.OrderListDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListVO {
    private Long orderId;
    private LocalDateTime createTime;
    private BigDecimal totalPrice;
    private Long couponId;
    private List<OrderListDTO> orderDTOList;
    private Integer status;
}
