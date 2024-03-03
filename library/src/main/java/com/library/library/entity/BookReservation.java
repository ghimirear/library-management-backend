package com.library.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import java.util.Date;


@Entity
@Data
public class BookReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;
    private Date borrowDate;
    private Date returnDate;
    private boolean returned = false;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name= "uid")
    private User user;
    @ManyToOne
    @JoinColumn(name = "bid")
    private Book book;


}
