package com.example.tea.entity.dto.Goods;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GoodsDTO {
    private Long goodsId;

    private String goodsName;

    private BigDecimal goodsPrice;

    private String goodsImage;
}
