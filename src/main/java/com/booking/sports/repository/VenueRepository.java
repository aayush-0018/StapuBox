package com.booking.sports.repository;

import com.booking.sports.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findBySport_Id(Long sportId);
}

