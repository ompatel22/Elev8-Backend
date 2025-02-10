package com.elev8.backend.hackathon.dto;

import lombok.Data;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Data
public class HackathonRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Organization is required")
    private String organization;

    @NotBlank(message = "Theme is required")
    private String theme;

    @NotBlank(message = "Mode is required")
    private String mode;

    @NotBlank(message = "About section is required")
    @Size(min = 20, message = "About section must be at least 20 characters")
    private String about;

    @NotBlank(message = "Participation type is required")
    private String participationType;

    private TeamSize teamSize;

    @NotNull(message = "Registration dates are required")
    private RegistrationDates registrationDates;

    private String createdBy;

    @Data
    public static class TeamSize {
        @Min(value = 1, message = "Minimum team size must be at least 1")
        private int min;

        @Min(value = 1, message = "Maximum team size must be at least 1")
        private int max;
    }

    @Data
    public static class RegistrationDates {
        @NotNull(message = "Start date is required")
        @Future(message = "Start date must be in the future")
        private LocalDateTime start;

        @NotNull(message = "End date is required")
        @Future(message = "End date must be in the future")
        private LocalDateTime end;
    }
}
