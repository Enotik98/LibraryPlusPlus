package com.libraryplusplus.repository;

import com.libraryplusplus.entity.ExtensionRequest;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.RequestStatus;
import com.libraryplusplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtensionRequestRepository extends JpaRepository<ExtensionRequest, Integer> {
    ExtensionRequest findById(int id);
    ExtensionRequest findAllByOrderAndStatus(Order order, RequestStatus requestStatus);
    ExtensionRequest findByUserAndOrder(User user, Order order);

    List<ExtensionRequest> findAllByUser(User user);
}
