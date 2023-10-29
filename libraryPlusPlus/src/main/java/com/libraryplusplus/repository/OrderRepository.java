package com.libraryplusplus.repository;

import com.libraryplusplus.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
}
