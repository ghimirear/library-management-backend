package com.library.library.service;

import com.library.library.entity.Book;
import com.library.library.entity.BookRating;
import com.library.library.entity.User;
import com.library.library.model.BookRatingId;
import com.library.library.model.RateBook;
import com.library.library.repository.BookRatingRepository;
import com.library.library.repository.BookRepository;
import com.library.library.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class BookService {
    // need uerRepository to check if the user is valid or not to add the book
    private final UserRepository userRepository;

    private final BookRepository bookRepository;
    private final BookRatingRepository bookRatingRepository;
    public BookService(BookRepository bookRepository, UserRepository userRepository, BookRatingRepository bookRatingRepository){
        this.bookRepository=bookRepository;
        this.userRepository=userRepository;
        this.bookRatingRepository=bookRatingRepository;
    }
    // save book

public ResponseEntity<String> saveBook(Book book, Long uid){

        Optional<User> user = userRepository.findById(uid);
        if(user.isPresent()){
        if(user.get().getIsOwner()){
            bookRepository.save(book);
            return ResponseEntity.ok("Successfully saved book");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not allowed");
        }} else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login first to save book");
}

    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    public ResponseEntity<String> rateBook(RateBook ratebook) {
       Optional<Book> optionalBook = bookRepository.findById(ratebook.getBid());
       Optional<User> optionalUser = userRepository.findById(ratebook.getUid());
        // if the book and user are valid
       if(optionalBook.isPresent() && optionalUser.isPresent()){
         Book book = optionalBook.get();
           BookRatingId bookRatingId = new BookRatingId();
           bookRatingId.setBookId(ratebook.getBid());
           bookRatingId.setUserId(ratebook.getUid());
           Optional<BookRating> optionalBookRating = bookRatingRepository.findById(bookRatingId);
           if(optionalBookRating.isPresent()){
               // updating the existing rating
               BookRating bookRating= optionalBookRating.get();
               bookRating.setRating(ratebook.getRating());
               bookRatingRepository.save(bookRating);
               return ResponseEntity.ok().body("update success");
           } else{
               // creating new rating
               BookRating bookRating = new BookRating();
               bookRating.setBookId(ratebook.getBid());
               bookRating.setUserId(ratebook.getUid());
               bookRating.setRating(ratebook.getRating());
               bookRatingRepository.save(bookRating);
               book.getRatings().add(bookRating);
               bookRepository.save(book);
               return ResponseEntity.ok().body("rate success success");
           }
       } else return ResponseEntity.badRequest().body("book does not exist");

    }
// delete book
    public ResponseEntity<String> deleteBook(Long bid, Long uid){
        Optional<User> optionalUser = userRepository.findById(uid);
        if (optionalUser.isPresent()){
        if(optionalUser.get().getIsOwner()){
           if(bookRepository.existsById(bid)){
               bookRepository.deleteById(bid);
               return ResponseEntity.ok("success");
           } else return ResponseEntity.badRequest().body("book does not exist");
        } else return  ResponseEntity.badRequest().body("not allowed to delete");
        } return ResponseEntity.badRequest().body("login first to delete book.");

    }
}
