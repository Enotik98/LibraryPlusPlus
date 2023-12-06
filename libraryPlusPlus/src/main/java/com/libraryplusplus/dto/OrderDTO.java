package com.libraryplusplus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.Status;
import com.libraryplusplus.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDTO {
    private int id;
    private int user_id;
    @NotNull(message = "Book_id is empty")
    @Min(value = 1, message = "Book id must between 1 and 9999.")
    @Max(value = 9999, message = "Book id must between 1 and 9999.")
    private int book_id;
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @NotNull
    @Future(message = "Field new return date was filled incorrect.")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date return_date;
    private Status status;
    private Boolean returnedLate;

    public static OrderDTO ConvertToDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setBook_id(order.getBook().getId());
        dto.setUser_id(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setReturn_date(order.getReturn_date());
        dto.setStatus(order.getStatus());
        dto.setReturnedLate(order.getReturnedLate());
        return dto;
    }
    public Order ConvertToOrder(User user, Book book){
        Order order = new Order();
//        order.setId(this.getId());
        order.setBook(book);
        order.setUser(user);
//        order.setOrderDate(this.getOrderDate());
        order.setReturn_date(this.getReturn_date());
//        order.setStatus(this.getStatus());
//        order.setReturnedLate(this.getReturnedLate());
        return order;
    }
}
