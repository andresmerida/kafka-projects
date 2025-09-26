package com.ms.booking_service.event;

import java.math.BigDecimal;

public record BookingEvent(Long userId, Long eventId, Integer ticketCount, BigDecimal totalPrice) {
}
