package com.booking.sports.dto.response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VenueResponse {

    private Long id;
    private String name;
    private String location;
    private String sportName;

}
