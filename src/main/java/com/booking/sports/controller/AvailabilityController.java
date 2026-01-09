package com.booking.sports.controller;

import com.booking.sports.dto.request.AvailabilityRequest;
import com.booking.sports.dto.response.AvailableVenueResponse;
import com.booking.sports.service.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues/available")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping
    public List<AvailableVenueResponse> getAvailableVenues(
            @Valid @RequestBody AvailabilityRequest request) {

        return availabilityService.getAvailableVenues(request);
    }
}
