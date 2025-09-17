package com.ms.inventory.dto;

import com.ms.inventory.entity.Venue;

public record EventInventoryResponse(String event, Integer capacity, Venue venue) { }
