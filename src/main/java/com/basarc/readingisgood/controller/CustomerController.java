package com.basarc.readingisgood.controller;


import com.basarc.readingisgood.dto.AddCustomerRequestDto;
import com.basarc.readingisgood.dto.AddCustomerResponseDto;
import com.basarc.readingisgood.api.ApiConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
public class CustomerController extends AbstractApiController {

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addNewCustomer(@RequestBody @Valid AddCustomerRequestDto request) {

        AddCustomerResponseDto response = AddCustomerResponseDto.builder()
                .id("1234").name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail()).build();

        return ok(response);
    }


}
