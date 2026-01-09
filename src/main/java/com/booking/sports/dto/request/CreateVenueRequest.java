package com.booking.sports.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateVenueRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotNull
    private Long sportId; // INTERNAL sport DB id

}

