package com.example.tea.service.impl;

import com.example.tea.entity.pojo.Cart.Cart;
import com.example.tea.entity.vo.Cart.CartVO;
import com.example.tea.mapper.CartMapper;
import com.example.tea.service.CartService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Override
    public void insert(Integer goodsId,Integer quantity) {
        Cart cart = Cart.builder()
                .goodsId(goodsId)
                .quantity(quantity)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .userId(ThreadLocalUserIdUtil.getCurrentId())
                .build();
        cartMapper.insert(cart);
    }

    @Override
    public void delete(List<Integer> goodsIds) {
        cartMapper.delete(goodsIds,ThreadLocalUserIdUtil.getCurrentId());
    }

    @Override
    public void update(Integer goodsId,Integer quantity) {
        Cart cart = Cart.builder()
                .goodsId(goodsId)
                .userId(ThreadLocalUserIdUtil.getCurrentId())
                .quantity(quantity)
                .updateTime(LocalDateTime.now())
                .build();
        cartMapper.update(cart);
    }

    @Override
    public List<CartVO> get() {
        return cartMapper.get(ThreadLocalUserIdUtil.getCurrentId());
    }
}
