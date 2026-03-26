package com.example.tea.entity.dto.Goods;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeckillGoodsMessageDTO implements Serializable {
    private Long goodsId;
    private Long userId;
}
