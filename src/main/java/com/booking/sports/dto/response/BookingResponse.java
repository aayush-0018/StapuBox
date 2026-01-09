package com.booking.sports.dto.response;

import com.booking.sports.entity.BookingStatus;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class BookingResponse {

    private Long bookingId;
    private Long slotId;
    private BookingStatus status;
    private LocalDateTime createdAt;
}

