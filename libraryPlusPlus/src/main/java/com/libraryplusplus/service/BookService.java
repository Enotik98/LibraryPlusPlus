package com.libraryplusplus.service;

import com.libraryplusplus.dto.BookDTO;
import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.repository.BookRepository;
import com.libraryplusplus.repository.OrderRepository;
import com.libraryplusplus.utils.CustomException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    OrderRepository orderRepository;

    public List<BookDTO> findAllBook() {
        try {
            List<Book> books = bookRepository.findAll();
            List<BookDTO> result = new ArrayList<>();
            for (Book book : books) {
                result.add(BookDTO.ConvertToDTO(book));
            }
            return result;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public Map<String, Object> findById(int id) {
        try {
            Book book = bookRepository.findById(id);
            if (book == null) {
                throw new CustomException(HttpStatus.NOT_FOUND, "Book not found");
            } else {
                boolean isAvailable = isAvailable(book);
                Map<String, Object> resp = new HashMap<>();
                resp.put("book", BookDTO.ConvertToDTO(book));
                resp.put("isAvailable", isAvailable);
                return resp;
            }
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void addBook(BookDTO bookDTO) {
        try {
            Book exBook = bookRepository.findByTitle(bookDTO.getTitle());
            if (exBook != null) {
                throw new CustomException(HttpStatus.CONFLICT, "A book with this title already exists");
            }
            Book book = bookDTO.ConvertToBook();
            bookRepository.save(book);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void updateBook(BookDTO bookDTO) {
        try {
            Book exBook = bookRepository.findById(bookDTO.getId());
            if (exBook == null) {
                throw new CustomException(HttpStatus.NOT_FOUND, "Book not found");
            }
            if (!exBook.getTitle().equals(bookDTO.getTitle())) {
                Book titleBook = bookRepository.findByTitle(bookDTO.getTitle());
                if (titleBook != null) {
                    throw new CustomException(HttpStatus.CONFLICT, "A book with this title already exists");
                }
            }
            Book book = bookDTO.ConvertToBook();
            book.setAdd_date(exBook.getAdd_date());
            bookRepository.save(book);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void deleteBook(int id) {
        try {
            List<Order> bookOrder = orderRepository.findAllByBookIdByStatus(id);
            boolean shouldDelete = bookOrder.stream().allMatch(order -> List.of("AWAITING", "CHECKOUT").contains(order.getStatus()));
            if (shouldDelete) {
                bookRepository.deleteById(id);
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "This book can't be deleted, it has unfinished orders.");
            }
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public boolean isAvailable(Book book) {
        int count = orderRepository.countTakenBook(book);
        return count < book.getQuantity();
    }
}
