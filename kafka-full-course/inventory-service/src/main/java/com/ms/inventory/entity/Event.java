package com.ms.inventory.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private Long id;
    private String name;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "left_capacity")
    private Integer leftCapacity;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
}
