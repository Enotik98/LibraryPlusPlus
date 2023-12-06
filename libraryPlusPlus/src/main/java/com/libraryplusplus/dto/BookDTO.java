package com.libraryplusplus.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Genre;
import com.libraryplusplus.utils.CapitalizeDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.print.attribute.standard.MediaSize;
import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
public class BookDTO {
    private int id;
    @NotEmpty(message = "Title mustn't be empty.")
    private String title;
    @NotEmpty(message = "Author mustn't be empty.")
    private String author;
    @Min(value = 1, message = "Genre mustn't be empty.")
    @NotNull(message = "Genre mustn't be empty.")
    private int genre;
    @NotEmpty(message = "ISBN mustn't be empty.")
    private String ISBN;
    @Min(value = 1, message = "Publication year mustn't be empty.")
//    @Pattern(regexp="^\\d+$", message="Publication year must contain only digits")
    @NotNull(message = "Publication year mustn't be empty.")
    private int publication_year;
//    @Pattern(regexp="^\\d+$", message="Quantity must contain only digits")
    @Min(value = 1, message = "Quantity mustn't be empty or be more than 1.")
    @NotNull(message = "Quantity mustn't be empty.")
    private int quantity;
    private String about;
    private Date add_date;
    private String path_img;

    public static BookDTO ConvertToDTO(Book book){
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre().getId());
        dto.setISBN(book.getISBN());
        dto.setPublication_year(book.getPublication_year());
        dto.setQuantity(book.getQuantity());
        dto.setAbout(book.getAbout());
        dto.setAdd_date(book.getAdd_date());
        dto.setPath_img(book.getPath_img());
        return dto;
    }
    public Book ConvertToBook(Genre genre){
        Book book = new Book();
        book.setId(this.getId());
        book.setTitle(this.getTitle());
        book.setAuthor(this.getAuthor());
        book.setGenre(genre);
        book.setISBN(this.getISBN());
        book.setPublication_year(this.getPublication_year());
        book.setQuantity(this.getQuantity());
        book.setAbout(this.getAbout());
        book.setAdd_date(this.getAdd_date());
        book.setPath_img(this.getPath_img());
        return book;
    }
}
