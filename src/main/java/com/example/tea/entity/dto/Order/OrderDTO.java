package com.example.tea.entity.dto.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private LocalDateTime createTime;
    private String goodsName;
    private String goodsImage;
    private BigDecimal goodsPrice;
    private Integer quantity;
    private String goodsIntro;
    private Long couponId;
    private BigDecimal totalPrice;
}
