package com.booking.sports.repository;

import com.booking.sports.entity.Booking;
import com.booking.sports.entity.Slot;
import com.booking.sports.entity.Venue;
import com.booking.sports.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    // ✅ USED BY SLOT CREATION (OVERLAP PREVENTION) — KEEP AS IS
    @Query("""
        SELECT s FROM Slot s
        WHERE s.venue.id = :venueId
          AND s.startTime < :endTime
          AND s.endTime > :startTime
    """)
    List<Slot> findOverlappingSlots(
            @Param("venueId") Long venueId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    // ✅ FIXED AVAILABILITY QUERY (THIS IS THE CHANGE)
    @Query("""
    SELECT DISTINCT s.venue
    FROM Slot s
    WHERE s.venue.sport.id = :sportId
      AND s.startTime < :endTime
      AND s.endTime > :startTime
      AND NOT EXISTS (
          SELECT b
          FROM Booking b
          WHERE b.slot = s
            AND b.status = com.booking.sports.entity.BookingStatus.BOOKED
      )
""")
    List<Venue> findAvailableVenues(
            @Param("sportId") Long sportId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}

