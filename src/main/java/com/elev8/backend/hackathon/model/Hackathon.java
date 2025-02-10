package com.elev8.backend.hackathon.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "hackathons")
public class Hackathon {
    @Id
    private String id;
    private String logo;          // Will store the file path or URL
    private String title;
    private String organization;
    private String theme;
    private String mode;          // online/offline
    private String about;
    private String participationType;  // individual/team
    private TeamSize teamSize;
    private RegistrationDates registrationDates;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;

    @Data
    public static class TeamSize {
        private int min;
        private int max;
    }

    @Data
    public static class RegistrationDates {
        private LocalDateTime start;
        private LocalDateTime end;
    }
}
