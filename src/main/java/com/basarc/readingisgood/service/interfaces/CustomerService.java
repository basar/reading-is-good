package com.basarc.readingisgood.service.interfaces;


import com.basarc.readingisgood.dto.AddCustomerRequestDto;
import com.basarc.readingisgood.dto.AddCustomerResponseDto;

public interface CustomerService {

    AddCustomerResponseDto addCustomer(AddCustomerRequestDto addCustomerRequestDto);

}
