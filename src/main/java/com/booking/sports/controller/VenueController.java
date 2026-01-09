package com.booking.sports.controller;

import com.booking.sports.dto.request.CreateVenueRequest;
import com.booking.sports.dto.response.VenueResponse;
import com.booking.sports.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public VenueResponse createVenue(
            @Valid @RequestBody CreateVenueRequest request) {
        return venueService.createVenue(request);
    }

    @GetMapping
    public List<VenueResponse> getAllVenues() {
        return venueService.getAllVenues();
    }
}

