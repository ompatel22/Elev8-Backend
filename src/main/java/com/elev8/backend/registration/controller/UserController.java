package com.elev8.backend.registration.controller;

import com.elev8.backend.registration.model.User;
import com.elev8.backend.registration.service.UserService;
import com.elev8.backend.registration.exception.UserAlreadyExistsException;
import com.elev8.backend.registration.exception.InvalidCredentialsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Adjust according to frontend URL
public class UserController {

    @Autowired
    private UserService userService;

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        try {
            User authenticatedUser = userService.authenticateUser(
                    loginRequest.getUsername(), loginRequest.getPassword()
            );
            return ResponseEntity.ok(authenticatedUser);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get user details
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            System.out.println(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
    @GetMapping("/details/{collegeName}")
    public ResponseEntity<?> getCollegeStudents(@PathVariable String collegeName) {

        try{
            List<User> perticularCollegeStudent = userService.getUserByCollegeName(collegeName);
            return ResponseEntity.ok(perticularCollegeStudent);
        }
        catch(Exception e){
            return ResponseEntity.status(400).body("No Student found of entered college name.");
        }
    }
}
