package com.example.tea.service;

public interface SeckillService {
    String seckill(Long goodsId);

    String initStock(Long goodsId, int stock);
}
