package com.libraryplusplus.controller;

import com.libraryplusplus.dto.ExtensionRequestDTO;
import com.libraryplusplus.service.ExtensionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/extension-request")
@RequiredArgsConstructor
public class ExtensionRequestController {
    @Autowired
    private final ExtensionRequestService extensionRequestService;
    @GetMapping("")
    public ResponseEntity<?> getAllRequests(){return ResponseEntity.ok(extensionRequestService.findAllRequest());}

    @PostMapping("")
    public ResponseEntity<?> addRequest(@RequestBody ExtensionRequestDTO extensionRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        extensionRequestDTO.setUser_id(Integer.parseInt(authentication.getName()));
        if (extensionRequestService.addExtensionRequest(extensionRequestDTO)){
            return ResponseEntity.ok("add successful");
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("")
    public ResponseEntity<?> updateRequest(@RequestBody ExtensionRequestDTO extensionRequestDTO){
        if (extensionRequestService.updateExtensionRequest(extensionRequestDTO)){
            return ResponseEntity.ok("update successful");
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}
