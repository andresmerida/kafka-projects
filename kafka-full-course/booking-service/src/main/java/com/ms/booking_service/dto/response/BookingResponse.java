package com.ms.booking_service.dto.response;

import java.math.BigDecimal;

public record BookingResponse(Long userId, Long eventId, Integer ticketCount, BigDecimal totalPrice) {
}
