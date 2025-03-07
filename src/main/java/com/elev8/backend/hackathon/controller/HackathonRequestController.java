package com.elev8.backend.hackathon.controller;

import com.elev8.backend.hackathon.dto.HackathonDTO;
import com.elev8.backend.hackathon.dto.HackathonRequestDTO;
import com.elev8.backend.hackathon.service.HackathonRequestService;
import jakarta.servlet.ServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HackathonRequestController {

    final private HackathonRequestService hackathonRequestService;

    HackathonRequestController(HackathonRequestService hackathonRequestService) {
        this.hackathonRequestService = hackathonRequestService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> createHackathonRequest(@RequestBody HackathonRequestDTO hackathonRequestDTO) {
        return ResponseEntity.ok(hackathonRequestService.createHackathonRequest(hackathonRequestDTO));
    }

    @GetMapping("request/{id}")
    public ResponseEntity<?> getHackathonRequestById(@PathVariable String id) {
        return ResponseEntity.ok(hackathonRequestService.getHackathonRequestById(id));
    }


    @GetMapping("/requests")
    public ResponseEntity<?> getAllHackathonRequests() {
        return ResponseEntity.ok(hackathonRequestService.getAllHackathonRequests());
    }

    @GetMapping("/requests/{createdBy}")
    public ResponseEntity<?> getHackathonRequests(@PathVariable String createdBy) {
            return ResponseEntity.ok(hackathonRequestService.getHackathonByCreatedBy(createdBy));
    }

    @GetMapping("/requests/status/{requestedBy}")
    public ResponseEntity<?> getHackathonRequestsStatus(@PathVariable String requestedBy) {
        return ResponseEntity.ok(hackathonRequestService.getHackathonByRequestedBy(requestedBy));
    }

    @PutMapping("/request/{id}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @PathVariable String status, ServletResponse servletResponse) {
        return ResponseEntity.ok(hackathonRequestService.updateHackathonRequest(id, status));
    }
}