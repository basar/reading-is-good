package com.basarc.readingisgood.repository;

import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findByCustomer(Customer customer, Pageable pageable);

    Optional<List<Order>> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
