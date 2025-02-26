package com.elev8.backend.hackathon.controller;

import com.cloudinary.Cloudinary;
import com.elev8.backend.hackathon.dto.HackathonDTO;
import com.elev8.backend.hackathon.model.Hackathon;
import com.elev8.backend.hackathon.service.HackathonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hackathons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Configure according to frontend
public class HackathonController {

    private final HackathonService hackathonService;
    private final Cloudinary cloudinary;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<Hackathon> createHackathon(@RequestParam(value = "logo", required = false) MultipartFile logo,
                                                     @RequestParam("data") String jsonData) {

        try {

            // Configure date format for parsing
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            HackathonDTO hackathonDTO = objectMapper.readValue(jsonData, HackathonDTO.class);

            // Process the logo file if it exists
            if (logo != null && !logo.isEmpty()) {
                // Handle file upload
                //image-upload
                Map data = this.cloudinary.uploader().upload(logo.getBytes(), Map.of());
                String url = data.get("url").toString();
                hackathonDTO.setLogo(url);
            }
            return ResponseEntity.ok(hackathonService.createHackathon(hackathonDTO));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Hackathon>> getAllActiveHackathons() {
        return ResponseEntity.ok(hackathonService.getAllActiveHackathons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hackathon> getHackathonById(@PathVariable String id) {
        return ResponseEntity.ok(hackathonService.getHackathonById(id));
    }

    //for testing
    @PostMapping("/upload/img")
    public ResponseEntity<?> uploadHackathonImg(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(hackathonService.uploadImage(file));
    }
}