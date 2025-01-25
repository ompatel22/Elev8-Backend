package com.elev8.backend.registration.service;

import com.elev8.backend.registration.model.User;
import com.elev8.backend.registration.repository.UserRepository;
import com.elev8.backend.registration.exception.UserAlreadyExistsException;
import com.elev8.backend.registration.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register new user
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        return userRepository.save(user);
    }

    // Authenticate user (login)
    public User authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Invalid username.");
        }

        User user = userOptional.get();

        // Check if provided password matches (consider hashing for production)
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid password.");
        }

        return user;
    }

    // Get user details by username
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found.");
        }
    }
}
