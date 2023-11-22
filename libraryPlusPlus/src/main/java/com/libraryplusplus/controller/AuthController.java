package com.libraryplusplus.controller;

import com.libraryplusplus.dto.AuthDTO;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.service.UserService;
import com.libraryplusplus.utils.CustomException;
import com.libraryplusplus.utils.PasswordUtils;
import com.libraryplusplus.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:8081/*")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody AuthDTO authDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(CustomException.bindingResultToString(bindingResult) + " Please fill correct in these fields.");
            }
            Map<String, String> response = new HashMap<>();

            userService.addUser(authDTO);
            User user = userService.getByEmail(authDTO.getEmail());
            response.put("Token", TokenUtils.generateAccessToken(user.getId(), user.getRole()));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO authDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(CustomException.bindingResultToString(bindingResult) + " Please fill correct in these fields.");
            }
            Map<String, String> response = new HashMap<>();

            User user = userService.getByEmail(authDTO.getEmail());
            if (user == null ){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email!");
            }
            if (user.getIsBlocked()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is blocked!");
            }
            if (PasswordUtils.verifyPassword(authDTO.getPassword(), user.getPassword())) {
                response.put("Token", TokenUtils.generateAccessToken(user.getId(), user.getRole()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password!");
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values.");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PostMapping("/token")
    public ResponseEntity<?> checkToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        Map<String, String> response = new HashMap<>();
        if (TokenUtils.validateToken(token)) {
            Claims userInfo = TokenUtils.getClaimsFromToken(token);
            System.out.println(userInfo);
            response.put("id", userInfo.get("id").toString());
            response.put("role", (String) userInfo.get("role"));
            return ResponseEntity.ok(response);
        } else {
            System.out.println("false token");
        }
        return ResponseEntity.ok("ok");
    }

}
