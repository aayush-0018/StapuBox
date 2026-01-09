package com.booking.sports.controller;

import com.booking.sports.dto.request.CreateBookingRequest;
import com.booking.sports.dto.response.BookingResponse;
import com.booking.sports.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponse bookSlot(
            @Valid @RequestBody CreateBookingRequest request) {

        return bookingService.bookSlot(request);
    }

    @PutMapping("/{id}/cancel")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }
}
