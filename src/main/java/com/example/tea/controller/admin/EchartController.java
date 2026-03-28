package com.example.tea.controller.admin;

import com.example.tea.entity.pojo.Result;
import com.example.tea.service.EchartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * (Admin管理员)大屏展示接口
 */
@RestController
@RequestMapping("/api/admin/echart")
public class EchartController {
    @Autowired
    private EchartService eChartService;
    /**
     * 订单数量展示
     * @return
     */
    @GetMapping("/showOrderCount")
    public Result showOrderCount(){
        return Result.success(eChartService.showOrderCount());
    }
    /**
     * 注册数量展示
     * @return
     */
    @GetMapping("/showRegistrationCount")
    public Result showRegistrationCount(){
        return Result.success(eChartService.showRegistrationCount());
    }
    /**
     * 购物种类数量展示
     * @return
     */
    @GetMapping("/showOrderCategoryCount")
    public Result showOrderCategoryCount(){
        return Result.success(eChartService.showOrderCategoryCount());
    }
}
