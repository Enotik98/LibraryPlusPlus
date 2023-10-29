package com.libraryplusplus.service;

import com.libraryplusplus.dto.FullOrderDTO;
import com.libraryplusplus.dto.OrderDTO;
import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.Status;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.repository.BookRepository;
import com.libraryplusplus.repository.OrderRepository;
import com.libraryplusplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    public List<FullOrderDTO> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<FullOrderDTO> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(FullOrderDTO.ConvertToDTO(order));
        }
        return result;
    }

    public Order findOrderById(int id) {
        return orderRepository.findById(id);
    }

    public String addOrder(OrderDTO orderDTO) {

        User user = userRepository.findById(orderDTO.getUser_id());
        if (user.getIsSanctions()){
            return "your account has sanctions!";
        }

        Book book = bookRepository.findById(orderDTO.getBook_id());
        Order order = orderDTO.ConvertToOrder(user, book);
        int quantity = book.getQuantity();
        //add booking
        if (quantity == 0) {
            return "error ";
        } else {
            quantity--;
            book.setQuantity(quantity);
            bookRepository.save(book);

            orderRepository.save(order);
        }
        return "add successful";
    }

    public boolean changeStatus(int id, Status status) {
        Order order = orderRepository.findById(id);
        if (order.getStatus().equals(status)) {
            return false;
        }
        if (status.equals(Status.RETURNED)) {
            Date currentDate = new Date();
            order.setReturnedLate(currentDate.after(order.getReturn_date()));

            Book book = bookRepository.findById(order.getBook().getId());
            int count = book.getQuantity() + 1;
            book.setQuantity(count);
            bookRepository.save(book);
        }
        order.setStatus(status);
        orderRepository.save(order);
        return true;
    }

    public boolean updateOrder(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId());
        if (order == null) {
            return false;
        }
        if (orderDTO.getOrderDate() != null) {
            order.setOrderDate(orderDTO.getOrderDate());
        }
        if (orderDTO.getReturn_date() != null) {
            order.setReturn_date(orderDTO.getReturn_date());
        }
        if (orderDTO.getReturnedLate() != null) {
            order.setReturn_date(orderDTO.getReturn_date());
        }
        orderRepository.save(order);
        return true;
    }

    public boolean deleteOrder(int id) {
        Order order = orderRepository.findById(id);
        if (order.getStatus().equals(Status.CHECKOUT)) {
            orderRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
