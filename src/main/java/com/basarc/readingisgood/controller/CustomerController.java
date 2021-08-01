package com.basarc.readingisgood.controller;


import com.basarc.readingisgood.dto.AddCustomerRequestDto;
import com.basarc.readingisgood.dto.AddCustomerResponseDto;
import com.basarc.readingisgood.api.ApiConstant;
import com.basarc.readingisgood.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
public class CustomerController extends AbstractApiController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addNewCustomer(@RequestBody @Valid AddCustomerRequestDto request) {
        return ok(customerService.addCustomer(request));
    }


}
