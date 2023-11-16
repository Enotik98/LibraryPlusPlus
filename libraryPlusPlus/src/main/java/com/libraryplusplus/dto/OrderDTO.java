package com.libraryplusplus.dto;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.Status;
import com.libraryplusplus.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDTO {
    private int id;
    private int user_id;
    @NotNull(message = "Book_id is empty")
    private int book_id;
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
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
//        order.setReturn_date(this.getReturn_date());
//        order.setStatus(this.getStatus());
//        order.setReturnedLate(this.getReturnedLate());
        return order;
    }
}
