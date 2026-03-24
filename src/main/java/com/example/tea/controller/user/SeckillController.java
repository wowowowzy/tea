package com.example.tea.controller.user;

import com.example.tea.entity.pojo.Result;
import com.example.tea.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    /**
     * 秒杀商品接口
     * @param goodsId
     * @return
     */
    @GetMapping
    public Result seckill(Long goodsId) {
        return Result.success(seckillService.seckill(goodsId));
    }

}
