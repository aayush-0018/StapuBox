package com.booking.sports.service;

import com.booking.sports.dto.request.CreateSlotRequest;
import com.booking.sports.dto.response.SlotResponse;
import com.booking.sports.entity.Slot;
import com.booking.sports.entity.Venue;
import com.booking.sports.repository.SlotRepository;
import com.booking.sports.repository.VenueRepository;
import org.springframework.stereotype.Service;
import com.booking.sports.exception.ResourceNotFoundException;
import com.booking.sports.exception.SlotOverlapException;

import java.util.List;

@Service
public class SlotService {

    private final SlotRepository slotRepository;
    private final VenueRepository venueRepository;

    public SlotService(SlotRepository slotRepository,
                       VenueRepository venueRepository) {
        this.slotRepository = slotRepository;
        this.venueRepository = venueRepository;
    }

    public SlotResponse createSlot(Long venueId, CreateSlotRequest request) {

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

        List<Slot> overlappingSlots =
                slotRepository.findOverlappingSlots(
                        venueId,
                        request.getStartTime(),
                        request.getEndTime()
                );

        if (!overlappingSlots.isEmpty()) {
            throw new SlotOverlapException("Slot overlaps with existing slot");
        }

        Slot slot = new Slot();
        slot.setVenue(venue);
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());

        Slot saved = slotRepository.save(slot);

        SlotResponse response = new SlotResponse();
        response.setId(saved.getId());
        response.setVenueId(venueId);
        response.setStartTime(saved.getStartTime());
        response.setEndTime(saved.getEndTime());

        return response;
    }
}

