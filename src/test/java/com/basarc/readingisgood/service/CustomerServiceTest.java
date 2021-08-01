package com.basarc.readingisgood.service;

import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.dto.AddCustomerRequestDto;
import com.basarc.readingisgood.exception.ReadingException;
import com.basarc.readingisgood.repository.CustomerRepository;
import com.basarc.readingisgood.service.impl.CustomerServiceImpl;
import com.basarc.readingisgood.service.interfaces.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomerServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testAddCustomerWhenSameEmailIsExist() {
        //given
        Customer customer = new Customer();
        final String existEmail = "sample@sample.com";
        customer.setEmail(existEmail);

        when(customerRepository.findByEmail(existEmail)).thenReturn(Optional.of(customer));

        AddCustomerRequestDto requestDto = new AddCustomerRequestDto();
        requestDto.setEmail(existEmail);
        assertThrows(ReadingException.class, () -> {
            customerService.addCustomer(requestDto);
        });

        verify(customerRepository).findByEmail(existEmail);
    }

    @Test
    public void testAddCustomerWhenEmailIsNotExist() {

        //given
        Customer customer = new Customer();
        customer.setId("12345");

        when(customerRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);

        AddCustomerRequestDto requestDto = new AddCustomerRequestDto();
        requestDto.setName("SampleName");
        requestDto.setSurname("SampleSurname");
        requestDto.setAddress("Sample address");
        requestDto.setEmail("sample@sample.com");

        assertNotNull(customerService.addCustomer(requestDto).getId());

        verify(customerRepository).findByEmail(any());
        verify(customerRepository).save(any());

    }


}



