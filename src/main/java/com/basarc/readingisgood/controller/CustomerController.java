package com.basarc.readingisgood.controller;


import com.basarc.readingisgood.api.AddCustomerRequest;
import com.basarc.readingisgood.api.AddCustomerResponse;
import com.basarc.readingisgood.api.ApiConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
public class CustomerController extends AbstractApiController {

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addNewCustomer(@RequestBody @Valid AddCustomerRequest request) {

        AddCustomerResponse response = AddCustomerResponse.builder()
                .id("1234").name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail()).build();

        return ok(response);
    }


}
