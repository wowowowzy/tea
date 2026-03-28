package com.example.tea.mapper;

import com.example.tea.entity.pojo.Echart.OrderCategory;
import com.example.tea.entity.pojo.Order.Order;
import com.example.tea.entity.pojo.User.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface EchartMapper {
    @Select("select * from `order`")
    List<Order> getAllOrder();

    @Select("select * from sys_user")
    List<User> getAllUser();

    @Select("select g.category_id,COUNT(*) as orderCount from `order` o left join goods g on g.goods_id = o.goods_id group by g.category_id")
    List<OrderCategory> showOrderCategoryCount();

    @Select("select count(*) from goods")
    Integer goodsTotal();

    @Select("select count(*) from `order`")
    Integer orderTotal();

    @Select("select count(*) from sys_user")
    Integer userTotal();

    @Select("select sum(total_price) from order_detail ")
    BigDecimal priceTotal();
}
