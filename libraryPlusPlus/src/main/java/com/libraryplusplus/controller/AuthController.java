package com.libraryplusplus.controller;

import com.libraryplusplus.dto.UserDTO;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.service.UserService;
import com.libraryplusplus.utils.PasswordUtils;
import com.libraryplusplus.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:8081/*")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserService userService;
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserDTO userDTO){
        if (userService.addUser(userDTO)){
            return ResponseEntity.ok("add successful");
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String password = body.get("password");

        User user = userService.getUserLogin(email);
        Map<String, String> response = new HashMap<>();
        //add check on isBlocked
        if (user.getIsBlocked()){
            response.put("error", "you are blocking");
            response.put("isBlocking", "true");
            return ResponseEntity.ok(response);
        }
        if (PasswordUtils.verifyPassword(password, user.getPassword())){
            response.put("AccessToken", TokenUtils.generateAccessToken(user.getId(), user.getRole()));
            System.out.println(response);
        }else {
            response.put("error", "not correct password");
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/token")
    public ResponseEntity<?> checkToken(@RequestBody Map<String, String> body){
        String token = body.get("token");
        Map<String, String > response = new HashMap<>();
        if (TokenUtils.validateToken(token)){
            Claims userInfo = TokenUtils.getClaimsFromToken(token);
            System.out.println(userInfo);
            response.put("id", userInfo.get("id").toString());
            response.put("role", (String) userInfo.get("role"));
            return ResponseEntity.ok(response);
        }else {
            System.out.println("false token");
        }
        return ResponseEntity.ok("ok");
    }
}
