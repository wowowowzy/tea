package com.example.tea.entity.pojo.Coupon;

import com.example.tea.utils.ThreadLocalUserIdUtil;
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
public class Coupon {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID（关联用户表）
     */
    private Long userId;

    /**
     * 优惠券描述（如"满100减20，全场通用"）
     */
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

    /**
     * 创建时间（数据库自动填充）
     */
    private LocalDateTime createTime;

    /**
     * 更新时间（数据库自动填充）
     */
    private LocalDateTime updateTime;

    // 可选：添加状态枚举常量，避免硬编码数字
    public static final Integer STATUS_UNUSED = 1;    // 未使用
    public static final Integer STATUS_USED = 2;      // 已使用
    public static final Integer STATUS_EXPIRED = 3;   // 已过期
    public static final Integer STATUS_INVALID = 4;   // 已作废
    public static boolean vaildate(Coupon coupon){
        return coupon.userId.equals(ThreadLocalUserIdUtil.getCurrentId())
                && coupon.endTime.isAfter(LocalDateTime.now())
                && coupon.status.equals(Coupon.STATUS_UNUSED);
        }
    }
