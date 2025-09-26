package com.ms.order_service.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private BigDecimal totalPrice;

    @Column(name = "quantity")
    private Integer totalCount;

    @CreationTimestamp
    @Column(name = "placed_at", updatable = false,  nullable = false)
    private LocalDateTime placedAt;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "event_id")
    private Long eventId;

}
