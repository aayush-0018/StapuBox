package com.booking.sports.service;

import com.booking.sports.dto.request.CreateVenueRequest;
import com.booking.sports.dto.response.VenueResponse;
import com.booking.sports.entity.Sport;
import com.booking.sports.entity.Venue;
import com.booking.sports.repository.SportRepository;
import com.booking.sports.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final SportRepository sportRepository;

    public VenueService(VenueRepository venueRepository,
                        SportRepository sportRepository) {
        this.venueRepository = venueRepository;
        this.sportRepository = sportRepository;
    }

    public VenueResponse createVenue(CreateVenueRequest request) {

        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new RuntimeException("Sport not found"));

        Venue venue = new Venue();
        venue.setName(request.getName());
        venue.setLocation(request.getLocation());
        venue.setSport(sport);

        Venue saved = venueRepository.save(venue);

        VenueResponse response = new VenueResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setLocation(saved.getLocation());
        response.setSportName(sport.getSportName());

        return response;
    }

    public List<VenueResponse> getAllVenues() {
        return venueRepository.findAll().stream().map(venue -> {
            VenueResponse res = new VenueResponse();
            res.setId(venue.getId());
            res.setName(venue.getName());
            res.setLocation(venue.getLocation());
            res.setSportName(venue.getSport().getSportName());
            return res;
        }).toList();
    }
}

