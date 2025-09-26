package com.ms.order_service.service;

import com.ms.order_service.client.InventoryServiceClient;
import com.ms.order_service.entities.Order;
import com.ms.booking_service.event.BookingEvent;
import com.ms.order_service.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;

    public OrderService(OrderRepository orderRepository,
                        InventoryServiceClient inventoryServiceClient) {
        this.orderRepository = orderRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @KafkaListener(topics = "booking_event", groupId = "order-service")
    public void listen(BookingEvent bookingEvent) {
        log.info("Received booking event: {}", bookingEvent);

        // create Order object for DB
        orderRepository.saveAndFlush(toEntity(bookingEvent));

        // update Inventory
        inventoryServiceClient.updateInventory(bookingEvent.eventId(), bookingEvent.ticketCount());
        log.info("Inventory updated for event: {}, less tickets: {} ",
                bookingEvent.eventId(), bookingEvent.ticketCount());
    }

    private Order toEntity(BookingEvent bookingEvent) {
        Order order = new Order();
        order.setCustomerId(bookingEvent.eventId());
        order.setEventId(bookingEvent.eventId());
        order.setTotalCount(bookingEvent.ticketCount());
        order.setTotalPrice(bookingEvent.totalPrice());
        return order;
    }
}
