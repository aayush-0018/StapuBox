package com.booking.sports.service;

import com.booking.sports.dto.request.CreateBookingRequest;
import com.booking.sports.dto.response.BookingResponse;
import com.booking.sports.entity.Booking;
import com.booking.sports.entity.BookingStatus;
import com.booking.sports.entity.Slot;
import com.booking.sports.repository.BookingRepository;
import com.booking.sports.repository.SlotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.booking.sports.exception.ResourceNotFoundException;
import com.booking.sports.exception.SlotAlreadyBookedException;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;

    public BookingService(BookingRepository bookingRepository,
                          SlotRepository slotRepository) {
        this.bookingRepository = bookingRepository;
        this.slotRepository = slotRepository;
    }

    // 🔒 CONCURRENCY SAFE BOOKING
    @Transactional
    public BookingResponse bookSlot(CreateBookingRequest request) {

        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

        Booking booking = bookingRepository
                .findBySlotIdForUpdate(slot.getId())
                .orElse(null);

        if (booking != null) {
            if (booking.getStatus() == BookingStatus.BOOKED) {
                throw new SlotAlreadyBookedException("Slot already booked");
            }
            booking.setStatus(BookingStatus.BOOKED);
        } else {
            booking = new Booking();
            booking.setSlot(slot);
            booking.setStatus(BookingStatus.BOOKED);
        }

        Booking saved = bookingRepository.save(booking);

        BookingResponse response = new BookingResponse();
        response.setBookingId(saved.getId());
        response.setSlotId(slot.getId());
        response.setStatus(saved.getStatus());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }


    // 🔓 CANCEL BOOKING
    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return;
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
