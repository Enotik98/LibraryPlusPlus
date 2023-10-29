package com.libraryplusplus.repository;

import com.libraryplusplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPhone(String phone);
    User findByEmail(String email);
    User findById(int id);

}
