package com.libraryplusplus.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@Table(name = "books", schema = "public")
@Entity(name = "books")
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "genre")
    private String genre;
    @Column(name = "isbn")
    private String ISBN;
    @Column(name = "publication_year")
    private int publication_year;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "about")
    private String about ; //type for text

}
