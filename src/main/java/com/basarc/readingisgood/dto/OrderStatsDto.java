package com.basarc.readingisgood.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderStatsDto {

    private String month;

    private long totalOrderCount;

    private long totalBookCount;

    private Double totalPurchasedAmount;

}
