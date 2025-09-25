package com.ms.booking_service.service;

import com.ms.booking_service.client.InventoryServiceClient;
import com.ms.booking_service.dto.request.BookingRequest;
import com.ms.booking_service.dto.response.BookingResponse;
import com.ms.booking_service.dto.response.EventInventoryResponse;
import com.ms.booking_service.entity.Customer;
import com.ms.booking_service.event.BookingEvent;
import com.ms.booking_service.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public BookingService(CustomerRepository customerRepository,
                          InventoryServiceClient inventoryServiceClient,
                          KafkaTemplate<String, BookingEvent> bookingEventKafkaTemplate) {
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.kafkaTemplate = bookingEventKafkaTemplate;
    }

    public BookingResponse createBooking(final BookingRequest request) {
        // check if user exists
        final Optional<Customer> customer = customerRepository.findById(request.userId());
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        // check if there is enough inventory
        final EventInventoryResponse eventInventoryResponse =
                inventoryServiceClient.getEventInventoryResponse(request.eventId());
        log.info("Inventory Response: {}", eventInventoryResponse);
        if (eventInventoryResponse.capacity() < request.ticketCount()) {
            throw new RuntimeException("Not enough capacity");
        }

        // create booking
        final BookingEvent  bookingEvent = createBookingEvent(request, eventInventoryResponse);

        // send booking to Order Service on a kafka Topic
        kafkaTemplate.send("booking_event", bookingEvent);
        log.info("Booking Event sent to kafka: {}", bookingEvent);
        return new BookingResponse(bookingEvent.getUserId(),
                bookingEvent.getEventId(),
                bookingEvent.getTicketCount(),
                bookingEvent.getTotalPrice());
    }

    private BookingEvent createBookingEvent(BookingRequest request,
                                            EventInventoryResponse eventInventoryResponse) {
        return BookingEvent.builder()
                .userId(request.userId())
                .eventId(eventInventoryResponse.eventId())
                .ticketCount(request.ticketCount())
                .totalPrice(eventInventoryResponse.ticketPrice().multiply(BigDecimal.valueOf(request.ticketCount())))
                .build();
    }
}
