package com.libraryplusplus.controller;

import com.libraryplusplus.dto.BookDTO;
import com.libraryplusplus.dto.LostBookDTO;
import com.libraryplusplus.service.BookService;
import com.libraryplusplus.service.LostBookService;
import com.libraryplusplus.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> getAllBook() {
        try {
            return ResponseEntity.ok(bookService.findAllBook());
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(bookService.findById(id));
        }catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/lostBook")
    public ResponseEntity<?> getAllLostBooks() {
        try {
            return ResponseEntity.ok(lostBookService.findAllLostBooks());
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/lostBook/{id}")
    public ResponseEntity<?> getLostBook(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(lostBookService.findLostBook(id));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(CustomException.bindingResultToString(bindingResult));
            }
            bookService.addBook(bookDTO);
            return ResponseEntity.ok("add successful");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PostMapping("/lostbook")
    public ResponseEntity<?> addLostBook(@RequestBody Map<String, String> body) {
        try {
            int book_id = Integer.parseInt(body.get("book_id"));
            lostBookService.addLostBookWithoutOrder(book_id);
            return ResponseEntity.ok("add successful");
        } catch (IllegalArgumentException FormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }

    }

    @PutMapping("")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO bookDTO) {
        try {
            bookService.updateBook(bookDTO);
            return ResponseEntity.ok("update successful");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteBook(@RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id"));
            bookService.deleteBook(id);
            return ResponseEntity.ok("delete successful");
        } catch (IllegalArgumentException numberFormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/lostBook")
    public ResponseEntity<?> deleteLostBook(@RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id"));
            lostBookService.deleteLostBook(id);
            return ResponseEntity.ok("delete successful");
        } catch (IllegalArgumentException numberFormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields have incorrect values");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

}
