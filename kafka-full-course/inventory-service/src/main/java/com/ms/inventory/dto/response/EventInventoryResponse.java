package com.ms.inventory.dto.response;

import com.ms.inventory.entity.Venue;

import java.math.BigDecimal;

public record EventInventoryResponse(Long eventId, String event, Integer capacity,
                                     BigDecimal ticketPrice, Venue venue) { }
