package com.example.tea.service.impl;

import com.example.tea.entity.pojo.Echart.DateWithNum;
import com.example.tea.entity.pojo.Echart.OrderCategory;
import com.example.tea.entity.pojo.Order.Order;
import com.example.tea.entity.pojo.User.User;
import com.example.tea.mapper.EchartMapper;
import com.example.tea.service.EchartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EchartServiceImpl implements EchartService {
    @Autowired
    private EchartMapper echartMapper;
    @Override
    public List<DateWithNum> showOrderCount() {
         List<Order> list =echartMapper.getAllOrder();
         //年分组
        Map<Integer, List<Order>> yearMap = list.stream().collect(Collectors.groupingBy(order -> order.getCreateTime().getYear()));
        List<DateWithNum> dateWithNums = yearMap.entrySet().stream().flatMap(entryYear -> {
            Integer year = entryYear.getKey();
            List<Order> orderList = entryYear.getValue();
            Map<Integer, List<Order>> monthList = orderList.stream().collect(Collectors.groupingBy(order -> order.getCreateTime().getMonthValue()));
            return monthList.entrySet().stream().map(entryMonth -> {
                Integer month = entryMonth.getKey();
                int count = entryMonth.getValue().size();
                return DateWithNum.builder()
                        .year(year)
                        .month(month)
                        .num(count)
                        .build();
            });
        }).toList();
        return dateWithNums;
    }

    @Override
    public List<DateWithNum> showRegistrationCount() {
        List<User> list =echartMapper.getAllUser();
        //年分组
        Map<Integer, List<User>> yearMap = list.stream().collect(Collectors.groupingBy(order -> order.getCreateTime().getYear()));
        List<DateWithNum> dateWithNums = yearMap.entrySet().stream().flatMap(entryYear -> {
            Integer year = entryYear.getKey();
            List<User> orderList = entryYear.getValue();
            Map<Integer, List<User>> monthList = orderList.stream().collect(Collectors.groupingBy(order -> order.getCreateTime().getMonthValue()));
            return monthList.entrySet().stream().map(entryMonth -> {
                Integer month = entryMonth.getKey();
                int count = entryMonth.getValue().size();
                return DateWithNum.builder()
                        .year(year)
                        .month(month)
                        .num(count)
                        .build();
            });
        }).toList();
        return dateWithNums;
    }

    @Override
    public List<OrderCategory> showOrderCategoryCount() {
        return echartMapper.showOrderCategoryCount();
    }
}
