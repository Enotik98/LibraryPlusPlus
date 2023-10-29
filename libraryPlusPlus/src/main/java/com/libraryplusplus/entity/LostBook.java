package com.libraryplusplus.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lost_books", schema = "public")
@Data
@Entity(name = "lost_books")
@Getter
@Setter
public class LostBook {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_lost")
    private Date dateLost;

    @PrePersist
    public void setDefaultValues(){
        if (dateLost == null){
            dateLost = new Date();
        }
    }
}
