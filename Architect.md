# Sports Booking Service – Architecture & Schema Design (v1)

This document defines the FINALIZED core domain model and database schema
for the Sports Venue Availability & Booking Service.

This file is the single source of truth for:
- Entities
- Relationships
- Constraints
- Indexes
- Design decisions

Do NOT change entities without updating this file.

---

## 1. High-Level Domain Model

Sport ──▶ Venue ──▶ Slot ──▶ Booking

Rules:
- A Sport can have many Venues
- A Venue belongs to exactly one Sport
- A Venue can have many Slots
- A Slot belongs to exactly one Venue
- A Slot can have at most ONE active Booking
- Booking is per slot (1 booking = 1 slot)

---

## 2. External Sports Source (MANDATORY)

Sports are fetched ONLY from:
https://stapubox.com/sportslist/

No sport names or codes are hardcoded.

Stored fields from API:
- sport_id (external numeric ID)
- sport_code (external string code)
- sport_name (display name)

---

## 3. Entity Definitions (FINAL)

### 3.1 Sport Entity

Purpose:
- Store sports fetched from Stapubox API
- Provide reference data for venues

```java

package com.booking.sports.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "slots",
        indexes = {
                @Index(
                        name = "idx_slot_venue_time",
                        columnList = "venue_id, start_time, end_time"
                )
        }
)
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;
}

package com.booking.sports.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "sports",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "sport_code"),
                @UniqueConstraint(columnNames = "sport_id")
        }
)
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // internal DB id

    @Column(name = "sport_id", nullable = false, unique = true)
    private Long externalSportId;

    @Column(name = "sport_code", nullable = false, unique = true)
    private String sportCode;

    @Column(name = "sport_name", nullable = false)
    private String sportName;
}

package com.booking.sports.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
@Table(
        name = "venues",
        indexes = {
                @Index(name = "idx_venue_sport", columnList = "sport_id")
        }
)
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id", nullable = false)
    private Sport sport;
}

package com.booking.sports.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

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

package com.booking.sports.entity;

public enum BookingStatus {
    BOOKED,
    CANCELLED
}

