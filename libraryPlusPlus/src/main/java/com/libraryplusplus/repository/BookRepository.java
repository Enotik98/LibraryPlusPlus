package com.libraryplusplus.repository;

import com.libraryplusplus.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findById(int id);
    Book findByTitle(String name);
}
