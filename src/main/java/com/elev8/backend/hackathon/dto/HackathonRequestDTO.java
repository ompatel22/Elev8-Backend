package com.elev8.backend.hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "request")
public class HackathonRequestDTO {
    @Id
    private String id;
    private String hackathonId;
    private String hackathonTitle;
    private String createdBy;
    private String requestedBy;
    private LocalDateTime requestedAt;
    private String status;
}

