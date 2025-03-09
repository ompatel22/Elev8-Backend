package com.elev8.backend.hackathon.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "hackathons")
public class Hackathon {
    @Id
    private String id;
    private String logo; // Will store the file path or URL
    private String title;
    private String organization;
    private String theme;
    private String location;
    private String mode;
    private String about;
    private TeamSize teamSize;
    private RegistrationDates registrationDates;
    private HackathonDates hackathonDates;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private int currentTeamSize;

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

    @Data
    public static class HackathonDates {
        private LocalDateTime start;
        private LocalDateTime end;
    }

    private List<String> requestsToJoin = new ArrayList<>();
    private List<String> acceptedUsers = new ArrayList<>();
    private List<String> rejectedUsers = new ArrayList<>();
}
