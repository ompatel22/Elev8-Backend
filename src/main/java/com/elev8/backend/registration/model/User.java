package com.elev8.backend.registration.model;

import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Display name is required")
    private String displayName;

    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Invalid email format"
    )
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "College name is required")

    private String collegeName;

    private String githubUsername;

    private String leetcodeUsername;

    private String codechefUsername;

    private String hackerrankUsername;

    private String codeforcesUsername;
}
