package com.example.tea.service.impl;

import com.example.tea.entity.dto.Goods.GoodsDTO;
import com.example.tea.entity.dto.Goods.GoodsQueryDTO;
import com.example.tea.entity.pojo.PageResult;
import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.Goods.GoodsVO;
import com.example.tea.mapper.GoodsMapper;
import com.example.tea.service.GoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public PageResult findAllGoods(GoodsQueryDTO goodsQueryDTO) {

        PageHelper.startPage(
                goodsQueryDTO.getPage()==null ? 1 : goodsQueryDTO.getPage(),
                goodsQueryDTO.getPageSize()==null ? 10 : goodsQueryDTO.getPageSize()
        );
        Page<GoodsDTO> page = goodsMapper.findAllGoods(goodsQueryDTO);
        return PageResult.builder()
                .total(page.getTotal())
                .records(page.getResult())
                .build();
    }

    @Override
    public Result findGoodById(Long goodsId) {
        GoodsVO goodsVO = goodsMapper.findGoodById(goodsId);
        return Result.success(goodsVO);
    }


}
