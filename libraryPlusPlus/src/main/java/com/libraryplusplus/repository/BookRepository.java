package com.libraryplusplus.repository;

import com.libraryplusplus.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findById(int id);
    Book findByTitle(String name);

    List<Book> findAllByQuantity(int quantity);

}
