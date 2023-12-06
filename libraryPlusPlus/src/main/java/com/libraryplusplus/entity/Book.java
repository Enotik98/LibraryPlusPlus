package com.libraryplusplus.entity;

import com.libraryplusplus.dto.BookDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    @ManyToOne
    @JoinColumn(name = "genre")
    private Genre genre;
    @Column(name = "isbn")
    private String ISBN;
    @Column(name = "publication_year")
    private int publication_year;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "about")
    private String about ; //type for text
    @Temporal(TemporalType.DATE)
    @Column(name = "add_date")
    private Date add_date;
    @Column(name = "path_img")
    private String path_img;

    @PrePersist
    public void setDefaultValues(){
        if (add_date == null){
            add_date = new Date();
        }
    }

}
