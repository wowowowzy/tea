package com.example.tea.controller.admin;

import com.example.tea.entity.pojo.Result;
import com.example.tea.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * (Admin管理员)秒杀商品接口
 */
@RestController
@RequestMapping("/api/admin/seckill")
public class seckillAdminController {
    @Autowired
    private SeckillService seckillService;

    /**
     * 设置秒杀商品库存
     * @param goodsId
     * @param stock
     * @return
     */

    @PostMapping("initStock")
    public Result initStock(Long goodsId, int stock) {
        return Result.success(seckillService.initStock(goodsId, stock));
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
