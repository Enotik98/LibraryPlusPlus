package com.libraryplusplus.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@TypeDef(name = "checkout_status", typeClass = SQLEnumType.class)
@Table(name = "orders", schema = "public")
@Data
@Entity(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date orderDate ;
    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private Date return_date;

    @Enumerated(EnumType.STRING)
    @Type(type = "checkout_status")
    @Column(name = "status")
    private Status status;

    @Column(name="returned_late")
    private Boolean returnedLate;

    @PrePersist
    public void setDefaultValues(){
        if (orderDate == null){
            orderDate = new Date();
        }
        if (return_date == null){
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            return_date = calendar.getTime();
        }
        if (status == null){
            status = Status.CHECKOUT;
        }
    }

}
