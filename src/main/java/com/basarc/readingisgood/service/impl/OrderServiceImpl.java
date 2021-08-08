package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.domain.Book;
import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.domain.Order;
import com.basarc.readingisgood.domain.enums.OrderStatus;
import com.basarc.readingisgood.dto.CreateOrderRequestDto;
import com.basarc.readingisgood.dto.OrderDto;
import com.basarc.readingisgood.dto.PageableListDto;
import com.basarc.readingisgood.exception.ReadingException;
import com.basarc.readingisgood.repository.OrderRepository;
import com.basarc.readingisgood.service.interfaces.BookService;
import com.basarc.readingisgood.service.interfaces.CustomerService;
import com.basarc.readingisgood.service.interfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final BookService bookService;

    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService,
                            BookService bookService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        addCustomTypeMapping();
    }

    @Transactional
    @Override
    public OrderDto createOrder(CreateOrderRequestDto requestDto) {

        //Find the customer
        final Customer customer = customerService.findCustomerById(requestDto.getCustomerId()).orElseThrow(() -> {
            log.error("Customer could not be found with id:{}", requestDto.getCustomerId());
            return new ReadingException(ApiResponseCode.DATA_INTEGRITY_ERROR);
        });
        //Find the book
        final Book book = bookService.findById(requestDto.getBookId()).orElseThrow(() -> {
            log.error("Book could not be found with id:{}", requestDto.getBookId());
            return new ReadingException(ApiResponseCode.DATA_INTEGRITY_ERROR);
        });
        //Set order status
        final OrderStatus orderStatus = bookService.decreaseStockIfEnoughBookAvailable(book.getId(), requestDto.getQuantity())
                ? OrderStatus.PURCHASED : OrderStatus.DECLINED;
        //Calculate total price
        final BigDecimal totalPrice = (orderStatus == OrderStatus.PURCHASED) ?
                book.getPrice().multiply(BigDecimal.valueOf(requestDto.getQuantity())) : null;

        Order order = new Order();
        order.setBook(book);
        order.setCustomer(customer);
        order.setQuantity(requestDto.getQuantity());
        order.setOrderStatus(orderStatus);
        order.setTotalPrice(totalPrice);
        //Save the order
        orderRepository.save(order);

        return convertToOrderDto(order);
    }

    @Override
    public OrderDto findOrderById(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order could not be found with id:{}", orderId);
            return new ReadingException(ApiResponseCode.DATA_INTEGRITY_ERROR);
        });

        return convertToOrderDto(order);
    }

    @Override
    public PageableListDto<OrderDto> findOrdersByCustomerId(String customerId, Integer offset, Integer limit, String sortBy) {

        //Find the customer
        final Customer customer = customerService.findCustomerById(customerId).orElseThrow(() -> {
            log.error("Customer could not be found with id:{}", customerId);
            return new ReadingException(ApiResponseCode.DATA_INTEGRITY_ERROR);
        });

        int page = (limit != 0 && offset != 0) ? offset / limit : 0;
        Page<Order> orders = orderRepository.findByCustomer(customer, PageRequest.of(page, limit, Sort.by(sortBy)));

        PageableListDto<OrderDto> result = new PageableListDto<>();
        result.setTotalCount(orders.getTotalElements());
        result.setRows(convertToOrderDtoList(orders.getContent()));

        return result;
    }

    private OrderDto convertToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }


    private List<OrderDto> convertToOrderDtoList(List<Order> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(this::convertToOrderDto).collect(Collectors.toList());
    }


    private void addCustomTypeMapping() {
        modelMapper.typeMap(Order.class, OrderDto.class).addMappings(mapper -> {
            mapper.map(Order::getId, OrderDto::setOrderId);
            mapper.map(src -> src.getCustomer().getId(), OrderDto::setCustomerId);
            mapper.map(src -> src.getBook().getId(), OrderDto::setBookId);
        });
    }

}
