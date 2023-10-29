package com.libraryplusplus.dto;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.Status;
import com.libraryplusplus.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FullOrderDTO {
    private int id;
    private User user;
    private Book book;
    private Date orderDate;
    private Date return_date;
    private Status status;
    private Boolean returnedLate;
    public static FullOrderDTO ConvertToDTO(Order order){
        FullOrderDTO dto = new FullOrderDTO();
        dto.setId(order.getId());
        dto.setBook(order.getBook());
        dto.setUser(order.getUser());
        dto.setOrderDate(order.getOrderDate());
        dto.setReturn_date(order.getReturn_date());
        dto.setStatus(order.getStatus());
        dto.setReturnedLate(order.getReturnedLate());
        return dto;
    }
    //in user delete password
}
