package com.libraryplusplus.controller;

import com.libraryplusplus.dto.UserDTO;
import com.libraryplusplus.entity.Role;
import com.libraryplusplus.service.UserService;
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
import java.util.Map;

@Controller
@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUser() {
        try {
            return ResponseEntity.ok(userService.getAllUser());
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int userId = Integer.parseInt(authentication.getName());
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PostMapping("/role")
    public ResponseEntity<?> updateUserRole(@RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id"));
            Role role = Role.valueOf(body.get("role").toUpperCase());

            userService.updateUserRole(id, role);
            return ResponseEntity.ok("update successful");
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PutMapping("/ticket")
    public ResponseEntity<?> updateUserTicket(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(CustomException.bindingResultToString(bindingResult));
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int userId = Integer.parseInt(authentication.getName());
            userDTO.setId(userId);

            userService.updateUser(userDTO);
            return ResponseEntity.ok("update successful");
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PostMapping("/restriction")
    public ResponseEntity<?> updateRestriction(@RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id"));
            boolean sanctions = Boolean.parseBoolean(body.get("sanctions"));
            boolean blocked = Boolean.parseBoolean(body.get("blocked"));
            userService.updateRestrictions(id, sanctions, blocked);
            return ResponseEntity.ok("update successful");
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }

    }
}
