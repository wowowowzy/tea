package com.example.tea.entity.dto.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayDTO {
    private Integer goodsId;
    private Integer quantity;
}
