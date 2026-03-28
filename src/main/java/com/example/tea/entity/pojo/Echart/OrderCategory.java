package com.example.tea.entity.pojo.Echart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCategory {
    private Long categoryId;
    private Integer orderCount;
}
