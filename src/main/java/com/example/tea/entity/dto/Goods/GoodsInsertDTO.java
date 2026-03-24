package com.example.tea.entity.dto.Goods;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class GoodsInsertDTO {
    private String goodsName;

    // 商品介绍
    private String goodsIntro;


    private BigDecimal goodsPrice;


    private Long categoryId;


    private Integer stockNum;

    // 图片路径
    private String goodsImage;
}
