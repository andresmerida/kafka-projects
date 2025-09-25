package com.ms.booking_service.client;

import com.ms.booking_service.dto.response.EventInventoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class InventoryServiceClient {

    private final RestClient restClient;

    public InventoryServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public EventInventoryResponse getEventInventoryResponse(Long eventId) {
        return restClient.get()
                .uri("/inventory/events/{eventId}", eventId)
                .retrieve()
                .body(EventInventoryResponse.class);
    }
}
