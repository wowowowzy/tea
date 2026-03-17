package com.example.tea.service;

import com.example.tea.entity.pojo.Cart.Cart;
import com.example.tea.entity.vo.Cart.CartVO;

import java.util.List;

public interface CartService {
    void insert(Integer goodsId,Integer quantity);

    void delete(List<Integer> goodIds);

    void update(Integer goodsId,Integer quantity);

    List<CartVO> get();
}
