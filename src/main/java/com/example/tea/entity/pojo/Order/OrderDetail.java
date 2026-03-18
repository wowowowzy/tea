package com.example.tea.entity.pojo.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 订单详情表实体类（纯MyBatis版本）
 *
 * @author 开发者
 * @date 2026-03-18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单编号（外部业务主键）
     */
    private String orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 优惠券ID（可为空）
     */
    private Long couponId;

    /**
     * 订单总价（单位：元）
     */
    private BigDecimal totalPrice;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
