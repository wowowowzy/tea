package com.example.tea.service;

import com.example.tea.entity.vo.Goods.GoodsVO;

import java.util.List;

public interface SeckillService {
    String seckill(Long goodsId);

    String initStock(Long goodsId, int stock);

    List<GoodsVO> getAllSeckillGoods();
}
