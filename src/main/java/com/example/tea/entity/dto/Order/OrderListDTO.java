package com.example.tea.entity.dto.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDTO {
    private String goodsName;
    private String goodsImage;
    private BigDecimal goodsPrice;
    private Integer quantity;
    private String goodsIntro;
}
