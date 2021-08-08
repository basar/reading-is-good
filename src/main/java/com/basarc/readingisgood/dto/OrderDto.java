package com.basarc.readingisgood.dto;

import com.basarc.readingisgood.domain.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDto implements Serializable {

    private String orderId;

    private OrderStatus orderStatus;

    private String customerId;

    private String bookId;

    private Integer quantity;

    private BigDecimal totalPrice;

    private LocalDateTime createdDate;

}
