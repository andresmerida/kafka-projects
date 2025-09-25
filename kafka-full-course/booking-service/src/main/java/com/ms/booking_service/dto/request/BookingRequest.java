package com.ms.booking_service.dto.request;

public record BookingRequest(Long userId, Long eventId, Integer ticketCount) {
}
