package com.library.library.service;
import com.library.library.entity.Book;
import com.library.library.entity.BookReservation;
import com.library.library.entity.User;
import com.library.library.exception.ResourceNotFoundException;
import com.library.library.model.BRModel;
import com.library.library.repository.BookRepository;
import com.library.library.repository.BookReservationRepository;
import com.library.library.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final BookReservationRepository bookReservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private ReservationService(BookReservationRepository bookReservationRepository,
                               UserRepository userRepository, BookRepository bookRepository){
        this.userRepository=userRepository;
        this.bookReservationRepository=bookReservationRepository;
        this.bookRepository=bookRepository;
    }
    public ResponseEntity<String> makeReservation(BRModel brModel) {
        // let's check both book and user are present or not
        Optional<User> optionalUser = userRepository.findById(brModel.getUid());
        Optional<Book> optionalBook = bookRepository.findById(brModel.getBid());
        if(optionalBook.isPresent() && optionalUser.isPresent()) {
            Optional<BookReservation> optionalBookReservation = bookReservationRepository.findByBookAndUser(optionalBook.get(), optionalUser.get());
            if (!optionalBookReservation.isPresent()) {
                User user = optionalUser.get();
                Book book = optionalBook.get();
                BookReservation bookReservation = new BookReservation();
                bookReservation.setBook(book);
                bookReservation.setUser(user);
                bookReservation.setBorrowDate(brModel.getBorrowDate());
                bookReservation.setReturnDate(brModel.getBorrowDate());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(brModel.getBorrowDate());
                calendar.add(calendar.DAY_OF_MONTH, brModel.getForDays());
                Date returnDate = calendar.getTime();
                bookReservation.setReturnDate(returnDate);
                bookReservationRepository.save(bookReservation);
                return ResponseEntity.ok("successfully reserved");
            }return ResponseEntity.badRequest().body("reserved already");
        } else return ResponseEntity.badRequest().body("error saving book reservation");
    }

    public ResponseEntity<List<BookReservation>> getUserReservation(Long uid) {
        Optional<User> optionalUser = userRepository.findById(uid);
        if(optionalUser.isPresent()){
            List<BookReservation> reservedBooks = bookReservationRepository.findByUser(optionalUser.get());
         List<BookReservation> notReturnedBooks =  reservedBooks.stream().filter(reservedBook-> !reservedBook.isReturned()).collect(Collectors.toList());
            notReturnedBooks.forEach(book -> book.getUser().setPassword(null));
            if(notReturnedBooks.size()>1){
                for (int i = 1; i < notReturnedBooks.size(); i++) {
                   notReturnedBooks.get(i).setUser(null);
                }
            }
            return ResponseEntity.ok(notReturnedBooks);
        } throw new ResourceNotFoundException("user does not exists");

    }

    public ResponseEntity<String> returnBook(Long rid) {
        Optional<BookReservation> reservedBook = bookReservationRepository.findById(rid);
        if(reservedBook.isPresent()){
           BookReservation book = reservedBook.get();
            book.setReturned(true);
            bookReservationRepository.save(book);
            return ResponseEntity.ok("successfully returned");
        } throw new ResourceNotFoundException("book or reservation does not exist");


    }

    public ResponseEntity<List<BookReservation>> getUserHistory(Long uid, Long aid) {
        // isOwner checks the user is verified admin or not.
        Optional<User> isOwner = userRepository.findById(aid);
        if(isOwner.isPresent() && isOwner.get().getIsOwner()){
            Optional<User> optionalUser = userRepository.findById(uid);
            if(optionalUser.isPresent()){
                List<BookReservation>userReservations =  bookReservationRepository.findByUser(optionalUser.get());
                // setting user null coz admin already has the user information.
                userReservations.forEach(reservation -> reservation.setUser(null));
                return ResponseEntity.ok(userReservations);
            } throw new ResourceNotFoundException("User doe snot exist");

        } throw new ResourceNotFoundException("ask admin to check user history");
    }
}
