package com.libraryplusplus.dto;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.LostBook;
import com.libraryplusplus.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LostBookDTO {
    private int id;
    private int book_id;
    private int order_id;
    private Date dateLost;
    public static LostBookDTO ConvertToDTO(LostBook book){
        LostBookDTO dto = new LostBookDTO();
        dto.setId(book.getId());
        dto.setBook_id(book.getBook().getId());
        dto.setOrder_id(book.getOrder().getId());
        dto.setDateLost(book.getDateLost());
        return dto;
    }
    public LostBook ConvertToLostBook(Book book, Order order){
        LostBook lostBook = new LostBook();
        lostBook.setId(this.getId());
        lostBook.setBook(book);
        lostBook.setOrder(order);
        lostBook.setDateLost(this.getDateLost());
        return lostBook;
    }
    public static LostBook CreatLostBook(Book book, Order order){
        LostBook lostBook = new LostBook();
        lostBook.setOrder(order);
        lostBook.setBook(book);
        return lostBook;
    }

}
