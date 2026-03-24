package com.example.tea.service;

import com.example.tea.entity.dto.Goods.GoodsInsertDTO;
import com.example.tea.entity.dto.Goods.GoodsQueryDTO;
import com.example.tea.entity.pojo.Goods.Goods;
import com.example.tea.entity.pojo.PageResult;
import com.example.tea.entity.vo.Goods.GoodsVO;

public interface GoodsService {
    PageResult findAllGoods(GoodsQueryDTO goodsQueryDTO);

    GoodsVO findGoodById(Long goodsId);


    // 新增商品
    void addGoods(GoodsInsertDTO dto);

    // 修改商品
    void updateGoods(Long goodsId,GoodsInsertDTO dto);

    // 删除商品
    void deleteGoods(Long goodsId);

    PageResult findAllGoodsAdmin(GoodsQueryDTO goodsQueryDTO);

    Goods findGoodByIdAdmin(Long goodsId);
}
