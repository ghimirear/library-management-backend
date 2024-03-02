package com.library.library.controller;

import com.library.library.entity.BookReservation;
import com.library.library.model.BRModel;
import com.library.library.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/books/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }
    @PostMapping
    public ResponseEntity<String> makeReservation(@RequestBody BRModel brModel){
        return reservationService.makeReservation(brModel);
    }
    // get all reservation for user
    @GetMapping("/{uid}")
    public ResponseEntity<List<BookReservation>> getUserReservation(@PathVariable Long uid){
        return reservationService.getUserReservation(uid);
    }
    @PutMapping("/{rid}")
    public ResponseEntity<String> returnBook(@PathVariable Long rid){
        return reservationService.returnBook(rid);
    }
    @GetMapping("/history/{uid}")
    public ResponseEntity<List<BookReservation>> getUserHistory(@PathVariable Long uid, @PathVariable Long aid){
        return reservationService.getUserHistory(uid, aid);
    }
}
