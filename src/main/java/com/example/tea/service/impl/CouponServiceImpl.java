package com.example.tea.service.impl;

import com.example.tea.entity.vo.Coupon.CouponVO;
import com.example.tea.mapper.CouponMapper;
import com.example.tea.service.CouponService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponMapper couponMapper;
    @Override
    public List<CouponVO> getCoupon() {
        List<CouponVO> list = couponMapper.getCoupon(ThreadLocalUserIdUtil.getCurrentId());
        return list;
    }
}
