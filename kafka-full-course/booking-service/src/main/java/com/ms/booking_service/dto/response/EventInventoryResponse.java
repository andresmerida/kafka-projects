package com.ms.booking_service.dto.response;

import java.math.BigDecimal;

public record EventInventoryResponse(Long eventId, String event, Integer capacity,
                                     BigDecimal ticketPrice, VenueResponse venue) {
}
