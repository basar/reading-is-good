package com.basarc.readingisgood.service.interfaces;


import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.dto.AddCustomerRequestDto;
import com.basarc.readingisgood.dto.AddCustomerResponseDto;

import java.util.Optional;

public interface CustomerService {

    AddCustomerResponseDto addCustomer(AddCustomerRequestDto addCustomerRequestDto);

    Optional<Customer> findCustomerById(String id);

}
