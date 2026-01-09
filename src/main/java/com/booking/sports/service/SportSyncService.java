package com.booking.sports.service;

import com.booking.sports.entity.Sport;
import com.booking.sports.repository.SportRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class SportSyncService {

    private static final String SPORTS_API =
            "https://stapubox.com/sportslist/";

    private final SportRepository sportRepository;
    private final RestTemplate restTemplate;

    public SportSyncService(
            SportRepository sportRepository,
            RestTemplate restTemplate
    ) {
        this.sportRepository = sportRepository;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    @Transactional
    public void syncSports() {

        Map<String, Object> response =
                restTemplate.getForObject(SPORTS_API, Map.class);

        List<Map<String, Object>> data =
                (List<Map<String, Object>>) response.get("data");

        for (Map<String, Object> item : data) {
            Long sportId = Long.valueOf(item.get("sport_id").toString());
            String sportCode = item.get("sport_code").toString();
            String sportName = item.get("sport_name").toString();

            Sport sport = sportRepository
                    .findByExternalSportId(sportId)
                    .orElseGet(Sport::new);

            sport.setExternalSportId(sportId);
            sport.setSportCode(sportCode);
            sport.setSportName(sportName);

            sportRepository.save(sport);
        }
    }
}

