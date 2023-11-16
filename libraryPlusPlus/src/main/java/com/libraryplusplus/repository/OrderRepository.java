package com.libraryplusplus.repository;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
    List<Order> findAllByUser(User user);

    @Query("select COUNT(o) from orders o where o.book = :book_id and o.status IN ('AWAITING', 'CHECKOUT')")
    int countTakenBook(@Param("book_id")Book book);

    @Query("select b.genre as book_genre, COUNT (o.book.id) as orders_count FROM orders o JOIN books b on o.book.id = b.id WHERE o.orderDate BETWEEN :startDate AND :endDate group by b.genre ORDER BY orders_count desc")
    List<Map<String, Integer>> getGenreReportByPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT b.title AS book_title, COUNT (o.book.id) AS orders_count FROM orders o JOIN books b ON o.book.id = b.id WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY b.id ORDER BY orders_count DESC ")
    List<Map<String, Integer>> getPopularityBook(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
