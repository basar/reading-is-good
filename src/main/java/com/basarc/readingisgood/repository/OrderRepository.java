package com.basarc.readingisgood.repository;

import com.basarc.readingisgood.domain.Customer;
import com.basarc.readingisgood.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findByCustomer(Customer customer, Pageable pageable);

}
