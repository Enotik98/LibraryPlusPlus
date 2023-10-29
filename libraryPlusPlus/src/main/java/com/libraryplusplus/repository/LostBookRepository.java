package com.libraryplusplus.repository;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.LostBook;
import com.libraryplusplus.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface LostBookRepository extends JpaRepository<LostBook, Integer> {
    LostBook findById(int id);
    LostBook findByBookAndOrderAndAndDateLost(Book book, Order order, Date current);

}
