package com.libraryplusplus.controller;

import com.libraryplusplus.dto.ExtensionRequestDTO;
import com.libraryplusplus.entity.RequestStatus;
import com.libraryplusplus.entity.Status;
import com.libraryplusplus.service.ExtensionRequestService;
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
import javax.validation.constraints.Null;

@Controller
@RestController
@RequestMapping("/extension_request")
@RequiredArgsConstructor
public class ExtensionRequestController {
    @Autowired
    private final ExtensionRequestService extensionRequestService;

    @GetMapping("")
    public ResponseEntity<?> getAllRequests() {
        try {
            return ResponseEntity.ok(extensionRequestService.findAllRequest());
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addRequest(@Valid @RequestBody ExtensionRequestDTO extensionRequestDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(CustomException.bindingResultToString(bindingResult));
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            extensionRequestDTO.setUser_id(Integer.parseInt(authentication.getName()));
            extensionRequestService.addExtensionRequest(extensionRequestDTO);
            return ResponseEntity.ok("add successful");
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateRequest(@Valid @RequestBody ExtensionRequestDTO extensionRequestDTO, BindingResult bindingResult) {
        try {
            System.out.println(extensionRequestDTO);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(CustomException.bindingResultToString(bindingResult));
            }
            if (extensionRequestDTO.getStatus() == null) {
                return ResponseEntity.badRequest().body("The status is incorrect");
            }
            if ((extensionRequestDTO.getUser_id()) == 0){
                return ResponseEntity.badRequest().body("The user id is incorrect");
            }
            extensionRequestService.updateExtensionRequest(extensionRequestDTO);
            return ResponseEntity.ok("update successful");
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
