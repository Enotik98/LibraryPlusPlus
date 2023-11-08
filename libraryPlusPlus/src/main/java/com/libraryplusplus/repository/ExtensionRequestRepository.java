package com.libraryplusplus.repository;

import com.libraryplusplus.entity.ExtensionRequest;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtensionRequestRepository extends JpaRepository<ExtensionRequest, Integer> {
    ExtensionRequest findById(int id);
    ExtensionRequest findByUserAndOrder(User user, Order order);
}
