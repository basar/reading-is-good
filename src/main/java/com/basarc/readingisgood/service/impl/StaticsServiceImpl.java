package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.dto.OrderStatsDto;
import com.basarc.readingisgood.service.interfaces.OrderService;
import com.basarc.readingisgood.service.interfaces.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaticsServiceImpl implements StatisticsService {


    private final OrderService orderService;

    @Autowired
    public StaticsServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public List<OrderStatsDto> getMonthlyOrderStats() {
        return orderService.findMonthlyOrderStats();
    }

}
