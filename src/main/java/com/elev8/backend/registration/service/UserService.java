package com.elev8.backend.registration.service;

import com.elev8.backend.collegechat.services.RoomService;
import com.elev8.backend.registration.model.User;
import com.elev8.backend.registration.repository.UserRepository;
import com.elev8.backend.registration.exception.UserAlreadyExistsException;
import com.elev8.backend.registration.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoomService roomService;

    // Register new user
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        List<User> users = this.userRepository.findByCollegeName(user.getCollegeName());
        if (users.isEmpty()) {
            this.roomService.createRoom(user.getCollegeName(), user);
        }
        this.roomService.joinRoom(user.getCollegeName(), user);
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
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public User updateUser(User user, String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();

            if(user.getUsername()!=null) {
                if (userRepository.existsByUsername(user.getUsername())) {
                    throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
                }
                userToUpdate.setUsername(user.getUsername());
            }

            if (user.getDisplayName() != null) {
                userToUpdate.setDisplayName(user.getDisplayName());
            }
            if (user.getEmail() != null) {
                userToUpdate.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                userToUpdate.setPassword(user.getPassword());
            }
            if (user.getCollegeName() != null) {
                userToUpdate.setCollegeName(user.getCollegeName());
            }
            if (user.getBio() != null) {
                userToUpdate.setBio(user.getBio());
            }
            if (user.getGithubUsername() != null) {
                userToUpdate.setGithubUsername(user.getGithubUsername());
            }
            if (user.getLeetcodeUsername() != null) {
                userToUpdate.setLeetcodeUsername(user.getLeetcodeUsername());
            }
            if (user.getLinkedinurl() != null) {
                userToUpdate.setLinkedinurl(user.getLinkedinurl());
            }
            if (user.getCodechefUsername() != null) {
                userToUpdate.setCodechefUsername(user.getCodechefUsername());
            }
            if (user.getInstagramusername() != null) {
                userToUpdate.setInstagramusername(user.getInstagramusername());
            }
            if (user.getTwitterusername() != null) {
                userToUpdate.setTwitterusername(user.getTwitterusername());
            }
            if(user.getResumeUrl()!=null) {
                userToUpdate.setResumeUrl(user.getResumeUrl());
            }
            if(user.getPortfolioUrl()!=null) {
                userToUpdate.setPortfolioUrl(user.getPortfolioUrl());
            }
            if(user.getGifUrl()!=null) {
                userToUpdate.setGifUrl(user.getGifUrl());
            }
            if (user.getCoverPhotoUrl() != null) {
                userToUpdate.setCoverPhotoUrl(user.getCoverPhotoUrl());
            }
            if(user.getEmoji()!=null) {
                userToUpdate.setEmoji(user.getEmoji());
            }
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<String> getAllUsersId(){
        return userRepository.findAll().stream().map(User::getId).collect(Collectors.toList());
    }

    public List<User> getAllUsersById(List<String> usersid) {
        return userRepository.findAllById(usersid);
    }
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
}
