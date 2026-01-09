package com.booking.sports.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@Getter
@Setter
@NoArgsConstructor
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

