package com.library.library.repository;

import com.library.library.entity.Book;
import com.library.library.entity.BookReservation;
import com.library.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {
    @Query("select b from BookReservation b where b.book = ?1 and b.user = ?2")
    Optional<BookReservation> findByBookAndUser(Book book, User user);

    @Query("select b from BookReservation b where b.user = ?1")
    List<BookReservation> findByUser(User user);

}
