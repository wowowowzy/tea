package com.example.tea.task;

import com.example.tea.entity.pojo.Coupon.Coupon;
import com.example.tea.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CouponTask {
    @Autowired
    private CouponMapper couponMapper;
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateExpiredCoupon() {
        System.out.println("每隔10分钟执行优惠卷判断：" + LocalDateTime.now());
        List<Coupon> couponList = couponMapper.getExpiredCoupon();
        List<Coupon> coupons = couponList.stream().map(coupon -> {
            coupon.setUpdateTime(LocalDateTime.now());
            coupon.setStatus(Coupon.STATUS_EXPIRED);
            coupon.setUseTime(LocalDateTime.now());
            return coupon;
        }).toList();
        couponMapper.updateExpiredCoupon(coupons);
    }
}
