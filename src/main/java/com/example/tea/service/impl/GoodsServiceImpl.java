package com.example.tea.service.impl;

import com.example.tea.entity.dto.Goods.GoodsInsertDTO;
import com.example.tea.entity.dto.Goods.GoodsQueryDTO;
import com.example.tea.entity.pojo.Goods.Goods;
import com.example.tea.entity.pojo.PageResult;
import com.example.tea.entity.vo.Goods.GoodsVO;
import com.example.tea.mapper.GoodsMapper;
import com.example.tea.service.GoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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
        Page<GoodsInsertDTO> page = goodsMapper.findAllGoods(goodsQueryDTO);
        return PageResult.builder()
                .total(page.getTotal())
                .records(page.getResult())
                .build();
    }

    @Override
    @Cacheable(value = "goods",key = "#goodsId")
    public GoodsVO findGoodById(Long goodsId) {
        return goodsMapper.findGoodById(goodsId);
    }

    // 新增
    @Override
    public void addGoods(GoodsInsertDTO dto) {
        goodsMapper.insert(dto);
    }

    // 修改
    @Override
    public void updateGoods(Long goodsId,GoodsInsertDTO dto) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(dto, goods);
        goods.setGoodsId(goodsId);
        goodsMapper.updateById(goods);
    }

    // 物理删除
    @Override
    public void deleteGoods(Long goodsId) {
        goodsMapper.deleteById(goodsId);
    }

    @Override
    public PageResult findAllGoodsAdmin(GoodsQueryDTO goodsQueryDTO) {
        PageHelper.startPage(
                goodsQueryDTO.getPage()==null ? 1 : goodsQueryDTO.getPage(),
                goodsQueryDTO.getPageSize()==null ? 10 : goodsQueryDTO.getPageSize()
        );
        Page<Goods> page = goodsMapper.findAllGoodsAdmin(goodsQueryDTO);
        return PageResult.builder()
                .total(page.getTotal())
                .records(page.getResult())
                .build();
    }

    @Override
    public Goods findGoodByIdAdmin(Long goodsId) {
        return goodsMapper.findGoodByIdAdmin(goodsId);
    }

}
