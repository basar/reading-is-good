package com.basarc.readingisgood.service;

import com.basarc.readingisgood.domain.Book;
import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.domain.Order;
import com.basarc.readingisgood.domain.enums.OrderStatus;
import com.basarc.readingisgood.dto.CreateOrderRequestDto;
import com.basarc.readingisgood.dto.OrderDto;
import com.basarc.readingisgood.dto.PageableListDto;
import com.basarc.readingisgood.exception.ReadingException;
import com.basarc.readingisgood.repository.OrderRepository;
import com.basarc.readingisgood.service.impl.OrderServiceImpl;
import com.basarc.readingisgood.service.interfaces.BookService;
import com.basarc.readingisgood.service.interfaces.CustomerService;
import com.basarc.readingisgood.service.interfaces.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {OrderServiceImpl.class, ModelMapper.class})
@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private BookService bookService;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void testFindOrderByIdWhenOrderIdNotExist() {

        final String orderId = "12345";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(ReadingException.class, () -> orderService.findOrderById(orderId));

        verify(orderRepository).findById(orderId);
    }

    @Test
    public void testFindOrderByIdWhenOrderIdExist() {

        final String orderId = "12345";
        final Order order = new Order();
        order.setId(orderId);

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        assertEquals(orderDto, orderService.findOrderById(orderId));

        verify(orderRepository).findById(orderId);
    }


    @Test
    public void testCreateOrderWhenCustomerIdNotValid() {

        CreateOrderRequestDto orderRequestDto = new CreateOrderRequestDto();
        orderRequestDto.setCustomerId("12345");

        when(customerService.findById(orderRequestDto.getCustomerId())).thenReturn(Optional.empty());
        assertThrows(ReadingException.class, () -> orderService.createOrder(orderRequestDto));

        verify(customerService).findById(orderRequestDto.getCustomerId());
    }

    @Test
    public void testCreateOrderWhenBookIdNotValid() {

        CreateOrderRequestDto orderRequestDto = new CreateOrderRequestDto();
        orderRequestDto.setCustomerId("12345");
        orderRequestDto.setBookId("54321");

        Customer customer = new Customer();
        customer.setId(orderRequestDto.getCustomerId());

        when(customerService.findById(orderRequestDto.getCustomerId())).thenReturn(Optional.of(customer));
        when(bookService.findById(orderRequestDto.getBookId())).thenReturn(Optional.empty());

        assertThrows(ReadingException.class, () -> orderService.createOrder(orderRequestDto));

        verify(customerService).findById(orderRequestDto.getCustomerId());
        verify(bookService).findById(orderRequestDto.getBookId());
    }


    @Test
    public void testCreateOrderWhenBookStockNotEnough() {

        CreateOrderRequestDto orderRequestDto = new CreateOrderRequestDto();
        orderRequestDto.setCustomerId("12345");
        orderRequestDto.setBookId("54321");
        orderRequestDto.setQuantity(2);

        Customer customer = new Customer();
        customer.setId(orderRequestDto.getCustomerId());

        Book book = new Book();
        book.setId(orderRequestDto.getBookId());
        book.setPrice(new BigDecimal(10));
        book.setStock(1);

        when(customerService.findById(anyString())).thenReturn(Optional.of(customer));
        when(bookService.findById(anyString())).thenReturn(Optional.of(book));
        when(bookService.decreaseStockIfEnoughBookAvailable(anyString(), anyInt())).thenReturn(false);
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        assertEquals(orderService.createOrder(orderRequestDto).getOrderStatus(), OrderStatus.DECLINED);

        verify(customerService).findById(anyString());
        verify(bookService).findById(anyString());
        verify(bookService).decreaseStockIfEnoughBookAvailable(anyString(), anyInt());
        verify(orderRepository).save(any(Order.class));
    }


    @Test
    public void testCreateOrderWhenBookStockEnough() {

        CreateOrderRequestDto orderRequestDto = new CreateOrderRequestDto();
        orderRequestDto.setCustomerId("12345");
        orderRequestDto.setBookId("54321");
        orderRequestDto.setQuantity(2);

        Customer customer = new Customer();
        customer.setId(orderRequestDto.getCustomerId());

        Book book = new Book();
        book.setId(orderRequestDto.getBookId());
        book.setPrice(new BigDecimal(10));
        book.setStock(3);

        when(customerService.findById(anyString())).thenReturn(Optional.of(customer));
        when(bookService.findById(anyString())).thenReturn(Optional.of(book));
        when(bookService.decreaseStockIfEnoughBookAvailable(anyString(), anyInt())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        OrderDto orderDto = orderService.createOrder(orderRequestDto);

        assertEquals(orderDto.getOrderStatus(), OrderStatus.PURCHASED);
        assertEquals(orderDto.getTotalPrice(), BigDecimal.valueOf(orderRequestDto.getQuantity())
                .multiply(book.getPrice()));

        verify(customerService).findById(anyString());
        verify(bookService).findById(anyString());
        verify(bookService).decreaseStockIfEnoughBookAvailable(anyString(), anyInt());
        verify(orderRepository).save(any(Order.class));
    }


    @Test
    public void testFindOrdersByCustomerIdWhenCustomerIdNotValid() {

        when(customerService.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ReadingException.class, () -> orderService.findOrdersByCustomerId("12345", 0,
                5, "createdDate"));
        verify(customerService).findById(anyString());
    }

    @Test
    public void testFindOrdersByCustomerId() {

        Customer customer = new Customer();
        customer.setId("12345");

        Order order1 = new Order();
        order1.setId("12345");

        Order order2 = new Order();
        order2.setId("12121");

        Page<Order> page = new PageImpl<>(Arrays.asList(order1, order2));

        when(customerService.findById(anyString())).thenReturn(Optional.of(customer));
        when(orderRepository.findByCustomer(customer, PageRequest.of(0, 5, Sort.by("createdDate"))))
                .thenReturn(page);

        PageableListDto<OrderDto> orders = orderService.findOrdersByCustomerId(customer.getId(), 0,
                5, "createdDate");

        assertEquals(orders.getTotalCount(), 2);
        assertNotNull(orders.getRows());

        verify(customerService).findById(anyString());
        verify(orderRepository).findByCustomer(any(Customer.class), any(Pageable.class));
    }


    @Test
    public void testFindOrdersBuCustomerIdWhenResultEmpty() {

        Customer customer = new Customer();
        customer.setId("12345");

        when(customerService.findById(anyString())).thenReturn(Optional.of(customer));
        when(orderRepository.findByCustomer(customer, PageRequest.of(0, 5, Sort.by("createdDate")))).
                thenReturn(new PageImpl<>(Collections.emptyList()));

        PageableListDto<OrderDto> orders = orderService.findOrdersByCustomerId(customer.getId(), 0,
                5, "createdDate");

        assertEquals(orders.getTotalCount(), 0);

        verify(customerService).findById(anyString());
        verify(orderRepository).findByCustomer(any(Customer.class), any(Pageable.class));
    }


    @Test
    public void testFindOrdersByDateIntervalWhenStartEndDateAreInvalid() {

        assertThrows(ReadingException.class, () -> orderService.findOrdersByDateInterval(null, null));
        assertThrows(ReadingException.class, () -> orderService.findOrdersByDateInterval(
                LocalDate.of(2020, 8, 14), null));
        assertThrows(ReadingException.class, () -> orderService.findOrdersByDateInterval(null,
                LocalDate.of(2018, 7, 24)));
        assertThrows(ReadingException.class, () -> orderService.findOrdersByDateInterval(LocalDate.of(2018, 7,
                24), LocalDate.of(2018, 7, 23)));

        assertThrows(ReadingException.class, () -> orderService.findOrdersByDateInterval(
                LocalDate.of(2018, 7, 24), LocalDate.of(2019, 7, 24)));
    }


    @Test
    public void testFindOrdersByDateIntervalWhenStartDateEndDateAreValid(){

        List<Order> orders = Arrays.asList(new Order(),new Order());

        final LocalDate startDate = LocalDate.of(2018,7,24);
        final LocalDate endDate =  LocalDate.of(2018,7,25);

        when(orderRepository.findAllByCreatedDateBetween(startDate.atStartOfDay(),
                endDate.atStartOfDay().plusDays(1)))
                .thenReturn(Optional.of(orders));

        assertNotNull(orderService.findOrdersByDateInterval(startDate,endDate));

        verify(orderRepository).findAllByCreatedDateBetween(startDate.atStartOfDay(),
                endDate.atStartOfDay().plusDays(1));
    }




}
