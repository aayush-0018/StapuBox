package com.booking.sports.repository;

import com.booking.sports.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {

    Optional<Sport> findBySportCode(String sportCode);

    Optional<Sport> findByExternalSportId(Long externalSportId);
}
