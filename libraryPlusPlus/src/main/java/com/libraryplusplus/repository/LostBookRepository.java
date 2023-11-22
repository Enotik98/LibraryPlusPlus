package com.libraryplusplus.repository;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.LostBook;
import com.libraryplusplus.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LostBookRepository extends JpaRepository<LostBook, Integer> {
    LostBook findById(int id);
    LostBook findByBookAndOrderAndAndDateLost(Book book, Order order, Date current);

    @Query("SELECT b.title AS title, b.author as author, b.quantity as quantity, COUNT (lb.id) AS lost_copies " +
            "FROM lost_books lb JOIN books b on lb.book.id = b.id " +
            "GROUP BY b.title, b.author, b.quantity")
    List<Map<String, Object>> getLostBookReport();

}
