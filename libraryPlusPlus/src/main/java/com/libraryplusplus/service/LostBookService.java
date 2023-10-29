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
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<LostBookDTO> findAllLostBooks(){
        List<LostBook> books = lostBookRepository.findAll();
        List<LostBookDTO> result = new ArrayList<>();
        for (LostBook book : books){
            result.add(LostBookDTO.ConvertToDTO(book));
        }
        return result;
    }
    public LostBookDTO  findLostBook(int id){
        LostBook book = lostBookRepository.findById(id);
        return LostBookDTO.ConvertToDTO(book);
    }
    public boolean addLostBook(int order_id){
        Order order = orderRepository.findById(order_id);
        Book book = order.getBook();
        User user = order.getUser();

        if (lostBookRepository.findByBookAndOrderAndAndDateLost(book, order, new Date()) != null){
            return false;
        }

        LostBook lostBook = LostBookDTO.CreatLostBook(book, order);
        lostBookRepository.save(lostBook);
        int count = book.getQuantity() - 1;
        book.setQuantity(count);
        bookRepository.save(book);

        user.setIsSanctions(true);
        userRepository.save(user);

        return true;
    }
    public boolean deleteLostBook(int id){
        lostBookRepository.deleteById(id);
        return true;
    }
}
