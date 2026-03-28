package com.example.tea.service;

import com.example.tea.entity.pojo.Echart.DateWithNum;
import com.example.tea.entity.pojo.Echart.EchartData;
import com.example.tea.entity.pojo.Echart.OrderCategory;

import java.util.List;

public interface EchartService {
    List<DateWithNum> showOrderCount();

    List<DateWithNum> showRegistrationCount();

    List<OrderCategory> showOrderCategoryCount();

    EchartData showData();
}
