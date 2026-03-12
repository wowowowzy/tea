package com.example.tea.entity.dto.Goods;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodsQueryDTO {
    private Integer page;

    private Integer pageSize;

    private String goodsName;
    //分类id
    private Integer categoryId;

}
