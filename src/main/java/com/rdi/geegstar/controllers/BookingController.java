package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.AcceptBookingRequest;
import com.rdi.geegstar.dto.requests.BookingRequest;
import com.rdi.geegstar.dto.response.AcceptBookingResponse;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> bookTalent(@Valid @RequestBody BookingRequest bookingRequest) {
        try {
            return ResponseEntity.status(CREATED).body(bookingService.bookTalent(bookingRequest));
        } catch (GeegStarException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @PatchMapping("/accept")
    public ResponseEntity<?> acceptBooking(@RequestBody AcceptBookingRequest acceptBookingRequest) {
        try {
            return ResponseEntity.ok().body(bookingService.acceptBooking(acceptBookingRequest));
        } catch (GeegStarException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @PatchMapping("/decline/{bookingId}")
    public ResponseEntity<?> declineBooking(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok().body(bookingService.declineBooking(bookingId));
        } catch (GeegStarException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
