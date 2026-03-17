package com.example.tea.controller.user;

import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.Cart.CartVO;
import com.example.tea.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 新增购物车
     * @param goodsId
     * @param quantity
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestParam Integer goodsId,@RequestParam Integer quantity){
        try {
            cartService.insert(goodsId,quantity);
            return Result.success();
        }catch (Exception e) {
            return Result.error("添加出错");
        }
    }

    /**
     * 删除购物车
     * @param goodsIds
     * @return
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(value = "goodsIds")  List<Integer> goodsIds){
        try {
            cartService.delete(goodsIds);
            return Result.success();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
    }

    /**
     * 修改购物车数量
     * @param goodsId
     * @param quantity
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestParam Integer goodsId, @RequestParam Integer quantity){
        try {
            cartService.update(goodsId,quantity);
            return Result.success();
        }catch (Exception e){
            return Result.error("更改失败");
        }
    }

    /**
     * 查询购物车
     * @return
     */
    @GetMapping("/get")
    public Result get(){
        try {
            List<CartVO> cartVOS = cartService.get();
            return Result.success(cartVOS);
        }catch (Exception e){
            return Result.error("查询失败");
        }
    }
}
