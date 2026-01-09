package com.booking.sports.service;

import com.booking.sports.dto.request.AvailabilityRequest;
import com.booking.sports.dto.response.AvailableVenueResponse;
import com.booking.sports.entity.Venue;
import com.booking.sports.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {

    private final SlotRepository slotRepository;

    public AvailabilityService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<AvailableVenueResponse> getAvailableVenues(
            AvailabilityRequest request) {

        List<Venue> venues = slotRepository.findAvailableVenues(
                request.getSportId(),
                request.getStartTime(),
                request.getEndTime()
        );

        return venues.stream().map(venue -> {
            AvailableVenueResponse res = new AvailableVenueResponse();
            res.setVenueId(venue.getId());
            res.setVenueName(venue.getName());
            res.setLocation(venue.getLocation());
            return res;
        }).toList();
    }
}
