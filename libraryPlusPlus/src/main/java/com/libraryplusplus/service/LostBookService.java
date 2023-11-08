package com.libraryplusplus.service;

import com.libraryplusplus.dto.LostBookDTO;
import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.LostBook;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.repository.BookRepository;
import com.libraryplusplus.repository.LostBookRepository;
import com.libraryplusplus.repository.OrderRepository;
import com.libraryplusplus.repository.UserRepository;
import com.libraryplusplus.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LostBookService {
    @Autowired
    LostBookRepository lostBookRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    public List<LostBookDTO> findAllLostBooks() {
        try {
            List<LostBook> books = lostBookRepository.findAll();
            List<LostBookDTO> result = new ArrayList<>();
            for (LostBook book : books) {
                result.add(LostBookDTO.ConvertToDTO(book));
            }
            return result;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public LostBookDTO findLostBook(int id) {
        try {
            LostBook book = lostBookRepository.findById(id);
            return LostBookDTO.ConvertToDTO(book);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void addLostBook(int order_id) {
        ///
        try {
            Order order = orderRepository.findById(order_id);
            Book book = order.getBook();
            User user = order.getUser();
            if (lostBookRepository.findByBookAndOrderAndAndDateLost(book, order, new Date()) != null) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Such a request has already been added");
            }
            user.setIsSanctions(true);
            userRepository.save(user);

            LostBook lostBook = LostBookDTO.CreatLostBook(book, order);
            lostBookRepository.save(lostBook);

            changeQuantityBook(book); //function

        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void addLostBookWithoutOrder(int book_id) {
        try {
            Book book = bookRepository.findById(book_id);
            LostBook lostBook = LostBookDTO.CreatLostBook(book, null);
            lostBookRepository.save(lostBook);
            changeQuantityBook(book);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.toString());
        }
    }

    public void deleteLostBook(int id) {
        try {
            lostBookRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private void changeQuantityBook(Book book) {
        try {
            int count = book.getQuantity() - 1;
            book.setQuantity(count);
            bookRepository.save(book);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.toString());
        }
    }
}
