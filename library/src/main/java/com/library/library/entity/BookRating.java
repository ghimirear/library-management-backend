package com.library.library.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.library.library.model.BookRatingId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(BookRatingId.class)
public class BookRating{
    @Id
    private Long userId;

    @Id
    private Long bookId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "bookId", insertable = false, updatable = false)
    private Book book;

    private int rating;


//    public void rateBook(Long userId, Long bookId, int rating) {
//        Optional<Book> optionalBook = bookRepository.findById(bookId);
//        if (optionalBook.isPresent()) {
//            Book book = optionalBook.get();
//            BookRatingId bookRatingId = new BookRatingId();
//            bookRatingId.setUserId(userId);
//            bookRatingId.setBookId(bookId);
//
//            Optional<BookRating> optionalBookRating = bookRatingRepository.findById(bookRatingId);
//            if (optionalBookRating.isPresent()) {
//                // Update existing rating
//                BookRating bookRating = optionalBookRating.get();
//                bookRating.setRating(rating);
//                bookRatingRepository.save(bookRating);
//            } else {
//                // Create new rating
//                BookRating bookRating = new BookRating();
//                bookRating.setUserId(userId);
//                bookRating.setBookId(bookId);
//                bookRating.setRating(rating);
//                bookRatingRepository.save(bookRating);
//
//                // Add the rating to the book's list of ratings
//                book.getRatings().add(bookRating);
//                bookRepository.save(book);
//            }
//        } else {
//            // Handle the case where the book with the given ID doesn't exist
//        }
//    }
//}

}
