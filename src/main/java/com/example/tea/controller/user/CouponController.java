package com.example.tea.controller.user;

import com.example.tea.entity.pojo.Result;
import com.example.tea.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * 查看优惠卷
     * @return
     */
    @GetMapping("/getCoupon")
    public Result getCoupon(){
        return Result.success(couponService.getCoupon());
    }
}
