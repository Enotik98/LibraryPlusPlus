package com.libraryplusplus.dto;

import com.libraryplusplus.entity.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class BookDTO {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String ISBN;
    private int publication_year;
    private int quantity;
    private String about;

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
        return book;
    }
}
