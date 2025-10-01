package com.ms.order_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class InventoryServiceClient {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    private final RestClient restClient;

    public InventoryServiceClient() {
        this.restClient = RestClient.create();
    }

    public void updateInventory(Long eventId,
                                Integer ticketBooked) {
        restClient.patch()
                .uri(inventoryServiceUrl + "/events/" + eventId + "/capacity/" + ticketBooked)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();
    }
}
