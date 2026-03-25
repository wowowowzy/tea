package com.example.tea.controller.admin;

import com.example.tea.annotation.OperLog;
import com.example.tea.entity.pojo.Result;
import com.example.tea.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Admin管理员)管理审批订单接口
 */
@RestController
@RequestMapping("/api/admin/goods")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;
    /**
     * 获取所有订单
     */
    @GetMapping("showApprovalList")
    public Result showApprovalList(){
        return Result.success(orderService.showApprovalList());
    }
    /**
     * 审批订单
     */
    @OperLog(module = "订单", type = "审批")
    @PostMapping("approval")
    public Result approval(Long orderId){
        orderService.approval(orderId);
        return Result.success();
    }
}
