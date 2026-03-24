package com.example.tea.service;

public interface SeckillService {
    public String seckill(Long goodsId);

    String initStock(Long goodsId, int stock);
}
