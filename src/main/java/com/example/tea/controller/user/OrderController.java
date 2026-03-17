package com.example.tea.controller.user;

import com.example.tea.entity.dto.Order.OrderPayDTO;
import com.example.tea.entity.pojo.Result;
import com.example.tea.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 支付接口
     * @param payDTOList
     * @return
     */
    @PostMapping("/pay")
    public Result pay(List<OrderPayDTO> payDTOList){
        orderService.pay(payDTOList);
        return Result.success();
    }
}
