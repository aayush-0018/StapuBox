package com.booking.sports.dto.response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor

public class SlotResponse {

    private Long id;
    private Long venueId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
