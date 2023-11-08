package com.libraryplusplus.repository;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
    List<Order> findAllByUser(User user);

    @Query("select COUNT(o) from orders o where o.book = :book_id and o.status IN ('AWAITING', 'CHECKOUT')")
    int countTakenBook(@Param("book_id")Book book);
}
