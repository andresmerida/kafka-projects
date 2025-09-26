package com.ms.inventory.service;

import com.ms.inventory.dto.response.EventInventoryResponse;
import com.ms.inventory.dto.response.VenueInventoryResponse;
import com.ms.inventory.entity.Event;
import com.ms.inventory.repository.EventRepository;
import com.ms.inventory.repository.VenueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public InventoryService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public List<EventInventoryResponse> getAllEvents() {
        final List<Event> events = eventRepository.findAll();

        return events.stream().map(e -> new EventInventoryResponse(
                e.getId(),
                e.getName(),
                e.getLeftCapacity(),
                e.getTicketPrice(),
                e.getVenue()
        )).toList();
    }

    public Optional<VenueInventoryResponse> getVenueInformation(Long venueId) {
        return venueRepository.findById(venueId).map(venue -> new VenueInventoryResponse(
                venue.getId(),
                venue.getName(),
                venue.getTotalCapacity()
        ));
    }

    public Optional<EventInventoryResponse> getEventInformation(Long eventId) {
        return eventRepository.findById(eventId).map(event -> new EventInventoryResponse(
                event.getId(),
                event.getName(),
                event.getLeftCapacity(),
                event.getTicketPrice(),
                event.getVenue()
        ));
    }

    public void updateEventCapacity(Long eventId, Integer ticketsBooked) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found for eventId: " + eventId));
        event.setLeftCapacity(event.getLeftCapacity() - ticketsBooked);
        eventRepository.saveAndFlush(event);
        log.info("Updated event capacity for eventId: {} with tickets booked: {} ", eventId, ticketsBooked);
    }
}
