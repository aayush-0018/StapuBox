package com.booking.sports.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "slot_id")
        },
        indexes = {
                @Index(name = "idx_booking_status", columnList = "status")
        }
)

@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false, unique = true)
    private Slot slot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

