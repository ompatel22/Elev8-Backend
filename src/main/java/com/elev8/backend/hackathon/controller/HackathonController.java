package com.elev8.backend.hackathon.controller;

import com.elev8.backend.hackathon.dto.HackathonRequest;
import com.elev8.backend.hackathon.model.Hackathon;
import com.elev8.backend.hackathon.service.HackathonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hackathons")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Configure according to frontend
public class HackathonController {
    private final HackathonService hackathonService;

    @PostMapping
    public ResponseEntity<Hackathon> createHackathon(@Valid @RequestBody HackathonRequest request) {
        return ResponseEntity.ok(hackathonService.createHackathon(request));
    }

    @GetMapping
    public ResponseEntity<List<Hackathon>> getAllActiveHackathons() {
        return ResponseEntity.ok(hackathonService.getAllActiveHackathons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hackathon> getHackathonById(@PathVariable String id) {
        return ResponseEntity.ok(hackathonService.getHackathonById(id));
    }
}