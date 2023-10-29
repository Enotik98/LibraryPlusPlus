package com.libraryplusplus.controller;


import com.libraryplusplus.dto.OrderDTO;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.Status;
import com.libraryplusplus.service.LostBookService;
import com.libraryplusplus.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@Controller
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final LostBookService lostBookService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        System.out.println(orderDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());
        orderDTO.setUser_id(userId);
        return ResponseEntity.ok(orderService.addOrder(orderDTO));
    }

    //put or post
    @PostMapping("/status")
    public ResponseEntity<?> updateOrderStatus(@RequestBody Map<String, String> body) {
        int id = Integer.parseInt(body.get("id"));
        Status status = Status.valueOf(body.get("status").toUpperCase());
        if (orderService.changeStatus(id, status)) {
            if (status.equals(Status.LOST)) {
                if (lostBookService.addLostBook(id)) {
                    return ResponseEntity.ok("update order status and add lostBook successful");
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.ok("update status successful");
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("cancel")
    public ResponseEntity<?> deleteOrder(@RequestBody Map<String, String> body) {
        int id = Integer.parseInt(body.get("id"));
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.ok("delete successful");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
