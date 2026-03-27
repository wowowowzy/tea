package com.example.tea.controller.admin;

import com.example.tea.entity.dto.Goods.GoodsInsertDTO;
import com.example.tea.entity.dto.Goods.GoodsQueryDTO;
import com.example.tea.entity.pojo.Result;
import com.example.tea.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * (Admin管理员)管理商品接口
 */
@RestController
@RequestMapping("/api/admin/goods")
public class GoodsAdminController {

    @Autowired
    private GoodsService goodsService;
    /**
     * 查询所有商品
     * @param goodsQueryDTO
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(GoodsQueryDTO goodsQueryDTO) {
        return Result.success(goodsService.findAllGoodsAdmin(goodsQueryDTO));
    }

    /**
     * 查询商品详情
     * @param goodsId
     * @return
     */
    @GetMapping("/findGoodById")
    public Result findGoodById(Long goodsId){
        return Result.success(goodsService.findGoodByIdAdmin(goodsId));
    }

    /**
     * 新增商品
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody GoodsInsertDTO dto) {
        goodsService.addGoods(dto);
        return Result.success("新增成功");
    }

    /**
     *  修改商品
     * @param id
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestParam(value = "goodsId") Long id,@RequestBody GoodsInsertDTO dto) {
        goodsService.updateGoods(id, dto);
        return Result.success("修改成功");
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(value = "goodsId") Long id) {
        goodsService.deleteGoods(id);
        return Result.success("删除成功");
    }
}
