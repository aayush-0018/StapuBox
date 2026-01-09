package com.booking.sports.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CreateBookingRequest {

    @NotNull
    private Long slotId;

}

