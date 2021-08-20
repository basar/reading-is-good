package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.domain.Book;
import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.domain.Order;
import com.basarc.readingisgood.domain.enums.OrderStatus;
import com.basarc.readingisgood.dto.CreateOrderRequestDto;
import com.basarc.readingisgood.dto.OrderDto;
import com.basarc.readingisgood.dto.OrderStatsDto;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final static long MAX_DAY_QUERY_INTERVAL = 90;

    private final MongoTemplate mongoTemplate;

    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final BookService bookService;

    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(MongoTemplate mongoTemplate, OrderRepository orderRepository,
                            CustomerService customerService,
                            BookService bookService, ModelMapper modelMapper) {
        this.mongoTemplate = mongoTemplate;
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
        final Customer customer = customerService.findById(requestDto.getCustomerId()).orElseThrow(() -> {
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
        log.info("New order has been saved: {}", order);

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
        final Customer customer = customerService.findById(customerId).orElseThrow(() -> {
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

    @Override
    public List<OrderDto> findOrdersByDateInterval(LocalDate startDate, LocalDate endDate) {

        if (startDate == null || endDate == null) {
            log.error("StartDate and EndDate must not be null.");
            throw new ReadingException(ApiResponseCode.BAD_REQUEST);
        }

        if (!startDate.isBefore(endDate)) {
            log.error("Invalid values for startDate {} and endDate {}", startDate, endDate);
            throw new ReadingException(ApiResponseCode.BAD_REQUEST);
        }

        if (ChronoUnit.DAYS.between(startDate, endDate) > MAX_DAY_QUERY_INTERVAL) {
            log.error("Duration between dates must not exceed the max interval");
            throw new ReadingException(ApiResponseCode.BAD_REQUEST);
        }

        List<Order> orders = orderRepository.findAllByCreatedDateBetween(startDate.atStartOfDay(),
                endDate.atStartOfDay().plusDays(1)).orElse(Collections.emptyList());

        return convertToOrderDtoList(orders);
    }


    @Override
    public List<OrderStatsDto> findMonthlyOrderStats() {

        final Aggregation aggregation = newAggregation(
                //Get only PURCHASED ones
                match(Criteria.where("orderStatus").is(OrderStatus.PURCHASED)),
                //We need two fields and month
                project("quantity", "totalPrice").and(DateOperators.Month.month("$createdDate")).as("month"),
                //Group to month and calculate other fields
                group("month").count().as("totalOrderCount")
                        .sum("quantity").as("totalBookCount")
                        .sum("totalPrice").as("totalPurchasedAmount"),
                //We have to re-projection to get rid of the _id field
                project("totalOrderCount", "totalBookCount", "totalPurchasedAmount")
                        .and("month").previousOperation(),
                //Sort by month
                sort(Sort.Direction.DESC, "month")
        );

        AggregationResults<OrderStatsDto> aggregationResults = mongoTemplate.aggregate(aggregation, Order.class,
                OrderStatsDto.class);

        return aggregationResults.getMappedResults();
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
