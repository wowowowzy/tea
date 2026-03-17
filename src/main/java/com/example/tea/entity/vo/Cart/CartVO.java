package com.example.tea.entity.vo.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class CartVO {
    private Integer goodsId;
    private Integer quantity;
    private String goodsName;
    private BigDecimal goodsPrice;
    private String goodsImage;
}
