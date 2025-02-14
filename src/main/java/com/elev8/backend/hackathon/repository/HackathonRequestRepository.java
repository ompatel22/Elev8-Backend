package com.elev8.backend.hackathon.repository;

import com.elev8.backend.hackathon.model.Hackathon;
import com.elev8.backend.hackathon.model.HackathonRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HackathonRequestRepository extends MongoRepository<HackathonRequest, String> {
    List<HackathonRequest> findByCreatedBy(String createdBy);
    List<HackathonRequest> findByRequestedBy(String requestedBy);
    HackathonRequest getById(String id);
}
