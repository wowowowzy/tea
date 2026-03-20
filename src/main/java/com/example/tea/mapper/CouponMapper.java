package com.example.tea.mapper;

import com.example.tea.entity.pojo.Coupon.Coupon;
import com.example.tea.entity.vo.Coupon.CouponVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CouponMapper {
    void insertQuestionCoupon(Coupon coupon);

    @Select("select * from coupon where user_id = #{id}")
    List<CouponVO> getCoupon(Long id);

    @Select("select * from coupon where user_id = #{id} and end_time > now() and status = 1")
    List<CouponVO> getUnusedCoupon(Long id);

    @Select("select * from coupon where id=#{couponId}")
    Coupon getCouponByCouponId(Long couponId);

    void useCoupon(Coupon coupon);

    @Select("select  * from coupon where status = 1 and end_time < NOW()")
    List<Coupon> getExpiredCoupon();

    void updateExpiredCoupon(List<Coupon> coupons);

}
