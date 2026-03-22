package com.example.tea.task;

import com.example.tea.entity.pojo.Coupon.Coupon;
import com.example.tea.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.System.out;

@Component
public class CouponTask {
    @Autowired
    private CouponMapper couponMapper;
    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateExpiredCoupon() {
        out.println("每隔30分钟执行优惠卷判断：" + LocalDateTime.now());
        List<Coupon> couponList = couponMapper.getExpiredCoupon();
        List<Coupon> coupons = couponList.stream().map(coupon -> {
            coupon.setUpdateTime(LocalDateTime.now());
            coupon.setStatus(Coupon.STATUS_EXPIRED);
            coupon.setUseTime(LocalDateTime.now());
            return coupon;
        }).toList();
        if(!coupons.isEmpty()){
            couponMapper.updateExpiredCoupon(coupons);
            out.println("更新"+coupons.size()+"条数据");
        }else out.println("暂无过期数据");
    }
}
