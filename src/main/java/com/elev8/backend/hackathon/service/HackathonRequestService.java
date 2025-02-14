package com.elev8.backend.hackathon.service;

import com.elev8.backend.hackathon.dto.HackathonDTO;
import com.elev8.backend.hackathon.dto.HackathonRequestDTO;
import com.elev8.backend.hackathon.model.HackathonRequest;
import com.elev8.backend.hackathon.repository.HackathonRequestRepository;
import com.mongodb.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Service
public class HackathonRequestService {

    final private HackathonRequestRepository hackathonRequestRepository;

    public HackathonRequestService(HackathonRequestRepository hackathonRequestRepository) {
        this.hackathonRequestRepository = hackathonRequestRepository;
    }

    public HackathonRequest createHackathonRequest(HackathonRequestDTO hackathonRequestDTO) {
        HackathonRequest hackathonRequest = new HackathonRequest();
        hackathonRequest.setHackathonId(hackathonRequestDTO.getHackathonId());
        hackathonRequest.setHackathonTitle(hackathonRequestDTO.getHackathonTitle());
        hackathonRequest.setCreatedBy(hackathonRequestDTO.getCreatedBy());
        hackathonRequest.setRequestedBy(hackathonRequestDTO.getRequestedBy());
        hackathonRequest.setRequestedAt(LocalDateTime.now());
        hackathonRequest.setStatus(hackathonRequestDTO.getStatus());
        return hackathonRequestRepository.save(hackathonRequest);
    }

    public List<HackathonRequest> getAllHackathonRequests() {
        return hackathonRequestRepository.findAll();
    }

    public List<HackathonRequest> getHackathonByCreatedBy(String createdBy) {
        return hackathonRequestRepository.findByCreatedBy(createdBy);
    }

    public List<HackathonRequest> getHackathonByRequestedBy(String requestedBy) {
        return hackathonRequestRepository.findByRequestedBy(requestedBy);
    }

    public HackathonRequest getHackathonRequest(String id,String status) {
        HackathonRequest hackathonRequest=hackathonRequestRepository.getById(id);
        hackathonRequest.setStatus(status);
        hackathonRequestRepository.save(hackathonRequest);
        return hackathonRequest;
    }

    public HackathonRequest getHackathonRequestById(String id) {
        return hackathonRequestRepository.findById(id).orElse(null);
    }
}
