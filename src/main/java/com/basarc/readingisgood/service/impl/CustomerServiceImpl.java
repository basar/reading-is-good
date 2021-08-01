package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.dto.AddCustomerRequestDto;
import com.basarc.readingisgood.dto.AddCustomerResponseDto;
import com.basarc.readingisgood.exception.ReadingException;
import com.basarc.readingisgood.repository.CustomerRepository;
import com.basarc.readingisgood.service.interfaces.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Transactional
    @Override
    public AddCustomerResponseDto addCustomer(AddCustomerRequestDto addCustomerRequestDto) {

        Optional<Customer> existCustomer = customerRepository.findByEmail(addCustomerRequestDto.getEmail());

        if (existCustomer.isPresent()) {
            log.debug("Customer already exist with the email:{}", addCustomerRequestDto.getEmail());
            throw new ReadingException(ApiResponseCode.CUSTOMER_ALREADY_DEFINED);
        }

        //Create a new customer
        Customer customer = new Customer(addCustomerRequestDto.getName(),
                addCustomerRequestDto.getSurname(),
                addCustomerRequestDto.getEmail(),
                addCustomerRequestDto.getAddress());

        //Save the customer
        Customer managed = customerRepository.save(customer);

        return AddCustomerResponseDto.builder()
                .id(managed.getId())
                .name(managed.getName()).surname(managed.getSurname())
                .email(managed.getEmail()).address(managed.getAddress()).build();
    }


}
