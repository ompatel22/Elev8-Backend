package com.elev8.backend.hackathon.repository;

import com.elev8.backend.hackathon.model.Hackathon;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HackathonRepository extends MongoRepository<Hackathon, String> {
    List<Hackathon> findByRegistrationDates_EndAfterOrderByRegistrationDates_StartAsc(LocalDateTime now);
    Optional<Hackathon> findById(String id);
}