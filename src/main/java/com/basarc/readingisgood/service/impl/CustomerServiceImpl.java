package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.dto.AddCustomerRequestDto;
import com.basarc.readingisgood.dto.AddCustomerResponseDto;
import com.basarc.readingisgood.exception.ReadingException;
import com.basarc.readingisgood.repository.CustomerRepository;
import com.basarc.readingisgood.service.interfaces.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    @Override
    public AddCustomerResponseDto addCustomer(AddCustomerRequestDto addCustomerRequestDto) {

        Optional<Customer> existCustomer = customerRepository.findByEmail(addCustomerRequestDto.getEmail());
        if (existCustomer.isPresent()) {
            log.debug("Customer already exist with the email:{}", addCustomerRequestDto.getEmail());
            throw new ReadingException(ApiResponseCode.CUSTOMER_ALREADY_DEFINED);
        }
        //Convert to customer
        Customer customer = convertToCustomer(addCustomerRequestDto);
        //Save the customer
        Customer managed = customerRepository.save(customer);
        //Convert to dto and return it
        return convertToAddCustomerResponseDto(managed);
    }

    @Override
    public Optional<Customer> findById(String id) {
        Assert.hasText(id, "Id must not be empty!");
        return customerRepository.findById(id);
    }

    private Customer convertToCustomer(AddCustomerRequestDto addCustomerRequestDto) {
        return modelMapper.map(addCustomerRequestDto, Customer.class);
    }

    private AddCustomerResponseDto convertToAddCustomerResponseDto(Customer customer) {
        return modelMapper.map(customer, AddCustomerResponseDto.class);
    }

}
