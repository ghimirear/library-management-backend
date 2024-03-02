package com.library.library.controller;

import com.library.library.entity.Book;
import com.library.library.model.RateBook;
import com.library.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService=bookService;
    }
    @PostMapping("/{uid}/book")
    public ResponseEntity<String> saveBook(@PathVariable("uid") Long uid, @RequestBody Book book){
        return bookService.saveBook(book, uid);
    }
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return bookService.getAllBooks();
    }
    @PostMapping("/books/rating")
    public ResponseEntity<String> rateBook(@RequestBody RateBook ratebook){
        System.out.println(ratebook);
        return bookService.rateBook(ratebook);

    }
    @DeleteMapping("/books/{bid}/{uid}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bid, @PathVariable Long uid){
       return bookService.deleteBook(bid, uid);
    }

}
