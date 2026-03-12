package com.example.tea.entity.pojo.Goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    /**
     * 商品ID（主键）
     */
    private Long goodsId;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 商品介绍
     */
    private String goodsIntro;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品分类ID
     */
    private Long categoryId;

    /**
     * 库存数量
     */
    private Integer stockNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /*
    * 商品头像
     */
    private String goodsImage;
}
