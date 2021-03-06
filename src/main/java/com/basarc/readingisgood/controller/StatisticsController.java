package com.basarc.readingisgood.controller;

import com.basarc.readingisgood.api.ApiConstant;
import com.basarc.readingisgood.api.ApiResponse;
import com.basarc.readingisgood.dto.OrderStatsDto;
import com.basarc.readingisgood.service.interfaces.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstant.Path.STATS)
@Validated
public class StatisticsController extends AbstractApiController {


    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderStatsDto>>> getMonthlyOrderStats() {
        return ok(statisticsService.getMonthlyOrderStats());
    }


}
