package com.example.tea.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.tea.entity.dto.Order.OrderAndGoodsDTO;
import com.example.tea.entity.dto.Order.OrderDTO;
import com.example.tea.entity.dto.Order.OrderListDTO;
import com.example.tea.entity.dto.Order.OrderPayDTO;
import com.example.tea.entity.pojo.Coupon.Coupon;
import com.example.tea.entity.pojo.Order.Order;
import com.example.tea.entity.pojo.Order.OrderDetail;
import com.example.tea.entity.vo.Order.OrderListVO;
import com.example.tea.mapper.CouponMapper;
import com.example.tea.mapper.GoodsMapper;
import com.example.tea.mapper.OrderMapper;
import com.example.tea.service.OrderService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    private final Snowflake snowflake = IdUtil.getSnowflake();
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pay(List<OrderPayDTO> payDTOList, Long couponId) throws Exception {
        Long orderId = snowflake.nextId();
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        List<Order> orderList = payDTOList.stream().map(DTO -> Order.builder()
                .userId(userId)
                .goodsId(DTO.getGoodsId())
                .orderId(orderId)
                .quantity(DTO.getQuantity())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build()).toList();
        orderMapper.pay(orderList);

        // 计算总价
        List<OrderAndGoodsDTO> list = goodsMapper.getTotalPrice(payDTOList);
        BigDecimal total = BigDecimal.valueOf(0);
        for (OrderPayDTO orderPayDTO : payDTOList) {
            Integer goodsOrderId = orderPayDTO.getGoodsId();
            Integer quantity = orderPayDTO.getQuantity();
            for (OrderAndGoodsDTO orderAndGoodsDTO : list) {
                Integer goodsId = orderAndGoodsDTO.getGoodsId();
                BigDecimal goodsPrice = orderAndGoodsDTO.getGoodsPrice();
                if (goodsId.equals(goodsOrderId)) {
                    BigDecimal price = new BigDecimal(quantity).multiply(goodsPrice);
                    total = price.add(total);
                }
            }
        }

        // 获取优惠券并且抵扣
        if (couponId != null) {
            Coupon coupon = couponMapper.getCouponByCouponId(couponId);
            BigDecimal minAmount = coupon.getMinAmount();
            BigDecimal reduceAmount = coupon.getReduceAmount();
            if (!Coupon.vaildate(coupon)) { // 修复逻辑：校验失败直接抛异常
                throw new Exception("优惠券无效（已过期/已使用）");
            }
            if (total.compareTo(minAmount) < 0) { // 金额不足抛异常
                throw new Exception("订单金额未达到优惠券使用门槛");
            }
            // 抵扣优惠券
            total = total.subtract(reduceAmount);
            Coupon buildCoupon = Coupon.builder()
                    .id(couponId)
                    .status(Coupon.STATUS_USED)
                    .useTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .orderId(orderId)
                    .build();
            couponMapper.useCoupon(buildCoupon);
        }

        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(String.valueOf(orderId))
                .userId(userId)
                .couponId(couponId)
                .totalPrice(total)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        orderMapper.insertDetail(orderDetail);
    }

    @Override
    public List<OrderListVO> getOrders() {
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        List<OrderDTO> orders = orderMapper.getOrders(userId);
        Map<Long, List<OrderDTO>> listMap = orders.stream()
                .collect(Collectors.groupingBy(OrderDTO::getOrderId));
        return listMap.entrySet().stream().map(entry -> {
            Long orderId = entry.getKey();
            List<OrderDTO> orderDTOS = entry.getValue();
            LocalDateTime createTime = orderDTOS.get(0).getCreateTime();
            Long couponId = orderDTOS.get(0).getCouponId();
            BigDecimal totalPrice = orderDTOS.get(0).getTotalPrice();
            List<OrderListDTO> list = orderDTOS.stream().map(orderDTO -> OrderListDTO.builder()
                    .goodsName(orderDTO.getGoodsName())
                    .goodsIntro(orderDTO.getGoodsIntro())
                    .goodsPrice(orderDTO.getGoodsPrice())
                    .quantity(orderDTO.getQuantity())
                    .goodsImage(orderDTO.getGoodsImage()).build()
            ).toList();
            return OrderListVO.builder()
                    .orderId(orderId)
                    .createTime(createTime)
                    .couponId(couponId)
                    .totalPrice(totalPrice)
                    .orderDTOList(list).build();
        }).toList();
    }

    @Override
    public OrderListVO getOrderById(Long orderId) {
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        List<OrderDTO> orders = orderMapper.getOrderById(orderId,userId);
        List<OrderListDTO> list = orders.stream().map(DTO -> {
            OrderListDTO orderListDTO = new OrderListDTO();
            BeanUtils.copyProperties(DTO, orderListDTO);
            return orderListDTO;
        }).toList();
        return OrderListVO.builder()
                .orderId(orderId)
                .createTime(orders.get(0).getCreateTime())
                .totalPrice(orders.get(0).getTotalPrice())
                .couponId(orders.get(0).getCouponId())
                .orderDTOList(list)
                .build();
    }
}
