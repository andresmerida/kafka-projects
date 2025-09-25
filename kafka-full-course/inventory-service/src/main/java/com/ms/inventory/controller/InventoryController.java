package com.ms.inventory.controller;

import com.ms.inventory.dto.response.EventInventoryResponse;
import com.ms.inventory.dto.response.VenueInventoryResponse;
import com.ms.inventory.exceptions.ResourceNotFoundException;
import com.ms.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventInventoryResponse>> getInventoryEvents() {
        return ResponseEntity.ok(inventoryService.getAllEvents());
    }

    @GetMapping("/venues/{venueId}")
    public ResponseEntity<VenueInventoryResponse> inventoryByVenueId(@PathVariable Long venueId) {
        final Optional<VenueInventoryResponse> optionalDto = inventoryService.getVenueInformation(venueId);
        return optionalDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Venue with id: " + venueId + " not found"));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventInventoryResponse> getEventById(@PathVariable Long eventId) {
        final Optional<EventInventoryResponse> eventDto = inventoryService.getEventInformation(eventId);

        return eventDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id: " + eventId + " not found"));
    }

}
