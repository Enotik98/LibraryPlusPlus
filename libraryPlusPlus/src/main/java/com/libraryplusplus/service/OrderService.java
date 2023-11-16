package com.libraryplusplus.service;

import com.libraryplusplus.dto.FullOrderDTO;
import com.libraryplusplus.dto.OrderDTO;
import com.libraryplusplus.entity.*;
import com.libraryplusplus.repository.BookRepository;
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
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    LostBookService lostBookService;

    public List<FullOrderDTO> findAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            List<FullOrderDTO> result = new ArrayList<>();
            for (Order order : orders) {
                result.add(FullOrderDTO.ConvertToDTO(order));
            }
            return result;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    public List<FullOrderDTO> findAllUserOrders(int user_id){
        try {
            User user = userRepository.findById(user_id);
            List<Order> orders = orderRepository.findAllByUser(user);
            List<FullOrderDTO> result = new ArrayList<>();
            for (Order order : orders) {
                result.add(FullOrderDTO.ConvertToDTOWithoutUser(order));
            }
            return result;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    public FullOrderDTO findOrderById(int id) {
        try {
            Order order = orderRepository.findById(id);
            if (order == null){
                throw new CustomException(HttpStatus.NOT_FOUND, "Order already exists");
            }
            return FullOrderDTO.ConvertToDTO(order);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void addOrder(OrderDTO orderDTO) {
        try {
            User user = userRepository.findById(orderDTO.getUser_id());
            if (user.getIsSanctions()) {
                throw new CustomException(HttpStatus.FORBIDDEN, "You have sanctions, please contact the administrator for detail information");
            }
            if (userService.isUserHaveTicket(user)) {
                throw new CustomException(HttpStatus.FORBIDDEN, "We don't have a generated electronic library card. Please, go to the profile");
            }
            Book book = bookRepository.findById(orderDTO.getBook_id());
            if (!bookService.isAvailable(book)) {
                throw new CustomException(HttpStatus.NOT_FOUND, "There aren't copies available to order");
            }
            Order order = orderDTO.ConvertToOrder(user, book);
            order.setStatus(Status.AWAITING);
            orderRepository.save(order);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void changeStatus(int id, Status status, Date date) {
        try {
            Order order = orderRepository.findById(id);
            if (order.getStatus().equals(status)) {
                throw new CustomException(HttpStatus.OK, "The status hasn't been changed");
            }
            if (status.equals(Status.CHECKOUT)){
                if (date != null && !order.getReturn_date().equals(date)){
                    order.setReturn_date(date);
                }
            }
            if (status.equals(Status.RETURNED)) {
                Date currentDate = new Date();
                order.setReturnedLate(currentDate.after(order.getReturn_date()));
            }
            if (status.equals(Status.LOST)) {
                lostBookService.addLostBook(order.getId());
            }
            order.setStatus(status);
            orderRepository.save(order);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void updateOrder(OrderDTO orderDTO) {
        try {
            Order order = orderRepository.findById(orderDTO.getId());
            if (order == null) {
                throw new CustomException(HttpStatus.NOT_FOUND, "The order wasn't found");
            }
            if (order.getUser().getId() != orderDTO.getUser_id() || order.getBook().getId() != orderDTO.getBook_id()){
                throw new CustomException(HttpStatus.NOT_FOUND, "User id or Book id doesn't match");
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
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void deleteOrder(int id, int user_id) {
        try {
            Order order = orderRepository.findById(id);
            User user = userRepository.findById(user_id);
            if (order.getUser().getId() != user_id || !user.getRole().equals(Role.LIBRARIAN)){
                throw new CustomException(HttpStatus.FORBIDDEN, "You can't cancel an order");
            }
            if (!order.getStatus().equals(Status.AWAITING)) {
                throw new CustomException(HttpStatus.NOT_FOUND, "The order can't be canceled");
            }
            orderRepository.deleteById(id);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
