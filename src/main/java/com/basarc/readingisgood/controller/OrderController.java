package com.basarc.readingisgood.controller;

import com.basarc.readingisgood.api.ApiConstant;
import com.basarc.readingisgood.api.ApiResponseMessage;
import com.basarc.readingisgood.dto.CreateOrderRequestDto;
import com.basarc.readingisgood.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(ApiConstant.ORDER)
public class OrderController extends AbstractApiController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderRequestDto request) {
        return ok(orderService.createOrder(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") String orderId) {
        return ok(orderService.findOrderById(orderId));
    }
}
