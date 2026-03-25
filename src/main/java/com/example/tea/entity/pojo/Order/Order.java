package com.example.tea.entity.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    // 主键ID（对应数据库id字段）
    private Integer id;
    // 用户ID（对应数据库user_id字段）
    private Long userId;
    // 商品ID（对应数据库goods_id字段）
    private Integer goodsId;
    private Long orderId;
    private Integer quantity;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
}
