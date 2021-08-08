package com.basarc.readingisgood.service.interfaces;

import com.basarc.readingisgood.domain.Order;
import com.basarc.readingisgood.dto.CreateOrderRequestDto;
import com.basarc.readingisgood.dto.OrderDto;
import com.basarc.readingisgood.dto.PageableListDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(CreateOrderRequestDto requestDto);

    OrderDto findOrderById(String orderId);

    PageableListDto<OrderDto> findOrdersByCustomerId(String customerId, Integer offset, Integer limit, String sortBy);
}
