package com.libraryplusplus.controller;


import com.libraryplusplus.dto.OrderDTO;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.Status;
import com.libraryplusplus.service.LostBookService;
import com.libraryplusplus.service.OrderService;
import com.libraryplusplus.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.findAllOrders());
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(orderService.findOrderById(id));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUserOrders(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int userId = Integer.parseInt(authentication.getName());
            return ResponseEntity.ok(orderService.findAllUserOrders(userId));

        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(CustomException.bindingResultToString(bindingResult) + " Please fill correct in these fields.");
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int userId = Integer.parseInt(authentication.getName());

            orderDTO.setUser_id(userId);
            orderService.addOrder(orderDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    //put or post
    @PostMapping("/status")
    public ResponseEntity<?> updateOrderStatus(@RequestBody Map<String, String> body) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            int id = Integer.parseInt(body.get("id"));
            Status status = Status.valueOf(body.get("status").toUpperCase());
            Date return_date = format.parse(body.get("return_date"));

            orderService.changeStatus(id, status, return_date);
            return ResponseEntity.ok("Update successful");
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> deleteOrder( @RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id"));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int userId = Integer.parseInt(authentication.getName());

            orderService.deleteOrder(id, userId);
            return ResponseEntity.ok("Delete successful");
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

}
