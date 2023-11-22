package com.libraryplusplus.dto;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.LostBook;
import com.libraryplusplus.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class LostBookDTO {
    private int id;
    @NotNull
    private Book book;
    private Order order;
    @Temporal(TemporalType.DATE)
    private Date dateLost;
    public static LostBookDTO ConvertToDTO(LostBook book){
        LostBookDTO dto = new LostBookDTO();
        dto.setId(book.getId());
        dto.setBook(book.getBook());
//        dto.setOrder(book.getOrder());
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
