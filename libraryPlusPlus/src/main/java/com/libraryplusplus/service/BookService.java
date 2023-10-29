package com.libraryplusplus.service;

import com.libraryplusplus.dto.BookDTO;
import com.libraryplusplus.entity.Book;
import com.libraryplusplus.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<BookDTO> findAllBook() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> result = new ArrayList<>();
        for (Book book : books) {
            result.add(BookDTO.ConvertToDTO(book));
        }
        return result;
    }

    public BookDTO findById(int id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            return null;
        } else {
            return BookDTO.ConvertToDTO(book);
        }
    }

    public boolean addBook(BookDTO bookDTO) {
        try {
            Book exBook = bookRepository.findByTitle(bookDTO.getTitle());
            if (exBook != null) {
                return false;
            }
            Book book = bookDTO.ConvertToBook();
            bookRepository.save(book);
            return true;
        } catch (Exception e) {
            System.out.println("add Book: " + e);
            return false;
        }
    }

    public boolean updateBook(BookDTO bookDTO) {
        Book exBook = bookRepository.findById(bookDTO.getId());
        if (exBook == null) {
            return false;
        }
        Book book = bookDTO.ConvertToBook();
        bookRepository.save(book);
        return true;
    }

    public boolean deleteBook(int id) {
        bookRepository.deleteById(id);
        return true;
    }
}
