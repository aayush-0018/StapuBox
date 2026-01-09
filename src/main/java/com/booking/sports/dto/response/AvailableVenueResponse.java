package com.booking.sports.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AvailableVenueResponse {

    private Long venueId;
    private String venueName;
    private String location;
}
