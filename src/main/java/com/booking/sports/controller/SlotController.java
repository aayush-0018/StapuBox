package com.booking.sports.controller;

import com.booking.sports.dto.request.CreateSlotRequest;
import com.booking.sports.dto.response.SlotResponse;
import com.booking.sports.service.SlotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venues/{venueId}/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping
    public SlotResponse createSlot(
            @PathVariable Long venueId,
            @Valid @RequestBody CreateSlotRequest request) {

        return slotService.createSlot(venueId, request);
    }
}

