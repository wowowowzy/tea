package com.example.tea.mapper;

import com.example.tea.entity.dto.Goods.GoodsDTO;
import com.example.tea.entity.dto.Goods.GoodsInsertDTO;
import com.example.tea.entity.dto.Goods.GoodsQueryDTO;
import com.example.tea.entity.dto.Order.OrderAndGoodsDTO;
import com.example.tea.entity.dto.Order.OrderPayDTO;
import com.example.tea.entity.pojo.Goods.Goods;
import com.example.tea.entity.vo.Goods.GoodsVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsMapper {
    Page<GoodsDTO> findAllGoods(GoodsQueryDTO goodsQueryDTO);

    @Select("select * from goods where goods_id=#{goodsId}")
    GoodsVO findGoodById(Long goodsId);

    List<OrderAndGoodsDTO> getTotalPrice(List<OrderPayDTO> payDTOList);

    void insert(GoodsInsertDTO dto);

    void updateById(Goods goods);

    void deleteById(Long goodsId);

    Page<Goods> findAllGoodsAdmin(GoodsQueryDTO goodsQueryDTO);

    @Select("select * from goods where goods_id=#{goodsId}")
    Goods findGoodByIdAdmin(Long goodsId);

    Integer updateStocks(List<OrderPayDTO> list);

    @Insert("insert into goods_seckill(goods_id,goods_name,goods_intro,goods_price,category_id,stock_num,goods_image) values(#{goodsId},#{goodsName},#{goodsIntro},#{goodsPrice},#{categoryId},#{stockNum},#{goodsImage})")
    void initStock(GoodsVO goodsVO);

    @Update("update goods_seckill set stock_num = stock_num - 1 where goods_id=#{goodsId}")
    void subSeckillStock(Long goodsId);

    @Select("select count(*) from goods_seckill where goods_id=#{goodsId}")
    Integer vaildateSeckill(Long goodsId);

    @Update("update goods_seckill set stock_num=#{stock} where goods_id= #{goodsId}")
    void updateSeckillStocks(Long goodsId,int stock);
}
