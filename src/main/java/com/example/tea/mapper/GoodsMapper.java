package com.example.tea.mapper;

import com.example.tea.entity.dto.Goods.GoodsDTO;
import com.example.tea.entity.dto.Goods.GoodsQueryDTO;
import com.example.tea.entity.vo.Goods.GoodsVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper {
    Page<GoodsDTO> findAllGoods(GoodsQueryDTO goodsQueryDTO);

    @Select("select * from goods where goods_id=#{goodsId}")
    GoodsVO findGoodById(Long goodsId);
}
