package com.libraryplusplus.repository;

import com.libraryplusplus.entity.ExtensionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtensionRequestRepository extends JpaRepository<ExtensionRequest, Integer> {
    ExtensionRequest findById(int id);
}
