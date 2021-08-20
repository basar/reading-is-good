package com.basarc.readingisgood.controller;

import com.basarc.readingisgood.api.ApiConstant;
import com.basarc.readingisgood.dto.CreateOrderRequestDto;
import com.basarc.readingisgood.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(ApiConstant.Path.ORDER)
@Validated
public class OrderController extends AbstractApiController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequestDto request) {
        return ok(orderService.createOrder(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") String orderId) {
        return ok(orderService.findOrderById(orderId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getOrdersByDateInterval(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ok(orderService.findOrdersByDateInterval(startDate, endDate));
    }
}
