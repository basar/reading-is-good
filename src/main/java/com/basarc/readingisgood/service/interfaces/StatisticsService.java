package com.basarc.readingisgood.service.interfaces;

import com.basarc.readingisgood.dto.OrderStatsDto;

import java.util.List;

public interface StatisticsService {

    List<OrderStatsDto> getMonthlyOrderStats();

}
