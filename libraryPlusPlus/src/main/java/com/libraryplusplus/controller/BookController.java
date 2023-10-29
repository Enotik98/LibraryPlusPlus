package com.libraryplusplus.controller;

import com.libraryplusplus.dto.BookDTO;
import com.libraryplusplus.dto.LostBookDTO;
import com.libraryplusplus.service.BookService;
import com.libraryplusplus.service.LostBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RestController
@RequestMapping("/book")
//@CrossOrigin(origins = "")
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private final BookService bookService;
    @Autowired
    private final LostBookService lostBookService;

    @GetMapping("")
    public ResponseEntity<?> getAllBook() {return ResponseEntity.ok(bookService.findAllBook());}
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable Integer id) {return ResponseEntity.ok(bookService.findById(id));}
    @GetMapping("/lostBook")
    public ResponseEntity<?> getAllLostBooks(){return ResponseEntity.ok(lostBookService.findAllLostBooks());}
    @GetMapping("/lostBook/{id}")
    public ResponseEntity<?> getLostBook(@PathVariable Integer id){return ResponseEntity.ok(lostBookService.findLostBook(id));}

    @PostMapping("")
    public ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO) {
        if (bookService.addBook(bookDTO)) {
            return ResponseEntity.ok("add successful");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
//    @PostMapping("/lostBook")
//    public ResponseEntity<?> addLostBook(@RequestBody LostBookDTO lostBookDTO){
//
////        if (lostBookService.addLostBook(lostBookDTO)){
////            return ResponseEntity.ok("add successful");
////        } else {
////            return ResponseEntity.badRequest().build();
////        }
//    }

    @PutMapping("")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO bookDTO) {
        if (bookService.updateBook(bookDTO)) {
            return ResponseEntity.ok("update successful");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteBook(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        if (bookService.deleteBook(id)) {
            return ResponseEntity.ok("delete successful");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/lostBook")
    public ResponseEntity<?> deleteLostBook(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        if (lostBookService.deleteLostBook(id)){
            return ResponseEntity.ok("delete successful");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }



}
