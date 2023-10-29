package com.libraryplusplus.controller;

import com.libraryplusplus.dto.UserDTO;
import com.libraryplusplus.entity.Role;
import com.libraryplusplus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.callback.SecretKeyCallback;
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
    public ResponseEntity<?> getAllUser(){return ResponseEntity.ok(userService.getAllUser());}

//    @PostMapping("/registration")
//    public ResponseEntity<?> registration(@RequestBody UserDTO userDTO){
//        System.out.println(userDTO);
//        if (userService.addUser(userDTO)){
//            return ResponseEntity.ok("add successful");
//        }else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
    @PostMapping("/role")
    public ResponseEntity<?> updateUserRole(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        Role role = Role.valueOf(body.get("role").toUpperCase());
        if (userService.updateUserRole(id, role)){
            return ResponseEntity.ok("update successful");
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/restriction")
    public ResponseEntity<?> updateRestriction(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        boolean sanctions = Boolean.parseBoolean(body.get("sanctions"));
        boolean blocked = Boolean.parseBoolean(body.get("blocked"));
        if (userService.updateRestrictions(id, sanctions, blocked)){
            return ResponseEntity.ok("update successful");
        }else {
            return ResponseEntity.badRequest().build();
        }

    }
}
