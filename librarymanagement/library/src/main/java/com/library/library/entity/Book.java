package com.library.library.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String name;
    private String author;
    private double rating;
    private Boolean isReservable = false;
    private Boolean isReference = false;
    @JsonManagedReference
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookRating> ratings;
}
