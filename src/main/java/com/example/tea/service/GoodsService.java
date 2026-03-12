package com.example.tea.service;

import com.example.tea.entity.dto.Goods.GoodsQueryDTO;
import com.example.tea.entity.pojo.PageResult;
import com.example.tea.entity.pojo.Result;

public interface GoodsService {
    PageResult findAllGoods(GoodsQueryDTO goodsQueryDTO);

    Result findGoodById(Long goodsId);

}
