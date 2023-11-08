package com.libraryplusplus.dto;

import com.libraryplusplus.entity.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class BookDTO {
    private int id;
    @NotEmpty(message = "Title mustn't be empty")
    private String title;
    @NotEmpty(message = "Author mustn't be empty")
    private String author;
    @NotEmpty(message = "Genre mustn't be empty")
    private String genre;
    @NotEmpty(message = "ISBN mustn't be empty")
    private String ISBN;
    @NotNull(message = "Publication year mustn't be empty")
    private int publication_year;
    @NotNull(message = "Quantity mustn't be empty")
    private int quantity;
    private String about;
    private Date add_date;
    private String path_img;

    public static BookDTO ConvertToDTO(Book book){
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setISBN(book.getISBN());
        dto.setPublication_year(book.getPublication_year());
        dto.setQuantity(book.getQuantity());
        dto.setAbout(book.getAbout());
        dto.setAdd_date(book.getAdd_date());
        dto.setPath_img(book.getPath_img());
        return dto;
    }
    public Book ConvertToBook(){
        Book book = new Book();
        book.setId(this.getId());
        book.setTitle(this.getTitle());
        book.setAuthor(this.getAuthor());
        book.setGenre(this.getGenre());
        book.setISBN(this.getISBN());
        book.setPublication_year(this.getPublication_year());
        book.setQuantity(this.getQuantity());
        book.setAbout(this.getAbout());
        book.setAdd_date(this.getAdd_date());
        book.setPath_img(this.getPath_img());
        return book;
    }
}
