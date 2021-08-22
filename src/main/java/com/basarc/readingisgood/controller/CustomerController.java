package com.basarc.readingisgood.controller;


import com.basarc.readingisgood.api.ApiResponse;
import com.basarc.readingisgood.dto.AddCustomerRequestDto;
import com.basarc.readingisgood.api.ApiConstant;
import com.basarc.readingisgood.dto.AddCustomerResponseDto;
import com.basarc.readingisgood.dto.OrderDto;
import com.basarc.readingisgood.dto.PageableListDto;
import com.basarc.readingisgood.service.interfaces.CustomerService;
import com.basarc.readingisgood.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@RestController
@Validated
@RequestMapping(ApiConstant.Path.CUSTOMER)
public class CustomerController extends AbstractApiController {

    private final CustomerService customerService;

    private final OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AddCustomerResponseDto>> addNewCustomer(
            @RequestBody @Valid AddCustomerRequestDto request) {
        return ok(customerService.addCustomer(request));
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<ApiResponse<PageableListDto<OrderDto>>> getOrdersByCustomerId(
            @PathVariable("customerId") String customerId,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") @Max(20) Integer limit,
            @RequestParam(defaultValue = "createdDate") String sortBy) {

        return ok(orderService.findOrdersByCustomerId(customerId, offset, limit, sortBy));
    }

}
