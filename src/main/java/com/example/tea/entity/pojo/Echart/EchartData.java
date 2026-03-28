package com.example.tea.entity.pojo.Echart;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EchartData {
    private Integer goodsTotal;
    private Integer orderTotal;
    private Integer userTotal;
    private BigDecimal priceTotal;
}
