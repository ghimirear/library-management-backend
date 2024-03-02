package com.library.library.repository;

import com.library.library.entity.BookRating;
import com.library.library.model.BookRatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, BookRatingId> {

}
