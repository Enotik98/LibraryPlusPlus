package com.libraryplusplus.repository;

import com.libraryplusplus.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findById(int id);
    Genre findByName(String name);
}
