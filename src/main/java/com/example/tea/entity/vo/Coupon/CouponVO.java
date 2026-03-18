package com.example.tea.entity.vo.Coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CouponVO {
    private  Long id;

    private String intro;

    /**
     * 满减金额（如20.00=减20元）
     */
    private BigDecimal reduceAmount;

    /**
     * 使用门槛（如100.00=满100元可用，无门槛则为0）
     */
    private BigDecimal minAmount;

    /**
     * 生效时间
     */
    private LocalDateTime startTime;

    /**
     * 失效时间
     */
    private LocalDateTime endTime;

    /**
     * 状态：1-未使用 2-已使用 3-已过期 4-已作废
     */
    private Integer status;

    /**
     * 使用时间（状态为2时不为空）
     */
    private LocalDateTime useTime;

    /**
     * 关联订单ID（使用后绑定）
     */
    private Long orderId;
}
