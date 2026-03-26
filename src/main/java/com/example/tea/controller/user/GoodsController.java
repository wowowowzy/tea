package com.example.tea.controller.user;

import com.example.tea.entity.dto.Goods.GoodsQueryDTO;
import com.example.tea.entity.pojo.Result;
import com.example.tea.service.GoodsService;
import com.example.tea.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SeckillService seckillService;

    /**
     * 商品全查找(包含分类查找)
     * @param goodsQueryDTO
     * @return
     */
    @GetMapping ("/findAllGoods")
    public Result findAllGoods(GoodsQueryDTO goodsQueryDTO){
        return Result.success(goodsService.findAllGoods(goodsQueryDTO));
    }

    /**
     * 查找商品详情
     * @param goodsId
     * @return
     */
    @GetMapping("/findGoodById")
    public Result findGoodById(Long goodsId){
        return Result.success(goodsService.findGoodById(goodsId));
    }

    /**
     * 获取所有秒杀商品
     * @return
     */
    @GetMapping("getAllSeckillGoods")
    public Result getAllSeckillGoods() {
        return Result.success(seckillService.getAllSeckillGoods());
    }
}
