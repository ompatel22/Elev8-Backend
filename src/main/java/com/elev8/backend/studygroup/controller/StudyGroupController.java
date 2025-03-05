package com.elev8.backend.studygroup.controller;
import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.registration.model.User;
import com.elev8.backend.studygroup.dto.CreateStudyGroupDTO;
import com.elev8.backend.studygroup.model.StudyGroup;
import com.elev8.backend.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/study_group")
@CrossOrigin(origins = "*")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;

    @PostMapping("/create_study_group")
    public ResponseEntity<StudyGroup> createStudyGroup(@RequestBody CreateStudyGroupDTO createStudyGroupDTO) {
        System.out.println("createStudyGroupDTO: " + createStudyGroupDTO);
        try{
            System.out.println(createStudyGroupDTO.getOwnerId());
            StudyGroup studyGroup = this.studyGroupService.createStudyGroup(createStudyGroupDTO.getStudyGroupName(), createStudyGroupDTO.getStudyGroupDescription(), createStudyGroupDTO.getOwnerId());
            return ResponseEntity.ok(studyGroup);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{studyGroupName}/join_study_group")
    public ResponseEntity<?> joinStudyGroup(
            @PathVariable String studyGroupName,
            @RequestBody Map<String, String> requestBody) { // Expecting JSON object

        String userId = requestBody.get("userId"); // Extract userId from JSON

        if (userId == null) {
            return ResponseEntity.badRequest().body("userId is required");
        }

        StudyGroup updatedStudyGroup = studyGroupService.joinStudyGroup(studyGroupName, userId);
        return ResponseEntity.ok(updatedStudyGroup);
    }


    @GetMapping("/get-all-study-groups")
    public ResponseEntity<List<StudyGroup>> getAllStudyGroups() {
        try{
            List<StudyGroup> studyGroups = this.studyGroupService.getAllStudyGroups();
            return ResponseEntity.ok(studyGroups);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{study_group_name}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String study_group_name,
                                                     @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                     @RequestParam(value = "size", defaultValue = "20", required = false) int size) {
        List<Message> messages = this.studyGroupService.getAllMessagesOfAStudyGroup(study_group_name);

        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);

        List<Message> paginatedMessages = messages.subList(start, end);
        return ResponseEntity.ok(paginatedMessages);
    }

    @GetMapping("/{study_group_name}/users")
    public ResponseEntity<List<User>> getAllUsersOfAStudyGroup(@PathVariable String study_group_name) {
        try{
            User owner = this.studyGroupService.getOwnerOfStudyGroup(study_group_name);
            List<User> users = this.studyGroupService.getAllUsersOfStudyGroup(study_group_name);

            users.remove(owner);
            users.add(0, owner);

            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping ("/{studyGroupName}/delete/{userId}")
    public ResponseEntity<?> deleteStudyGroup(@PathVariable String studyGroupName, @PathVariable String userId) {
        try{
            boolean deleted = this.studyGroupService.deleteStudyGroup(studyGroupName, userId);
            return ResponseEntity.ok(deleted);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{studyGroupName}/remove/{userId}")
    public ResponseEntity<?> deleteUserOfStudyGroup(@PathVariable String studyGroupName, @PathVariable String userId) {
        try{
            boolean deleted = this.studyGroupService.removeStudyGroupMember(studyGroupName, userId);
            return ResponseEntity.ok(deleted);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{studyGroupName}/user/{userId}")
    public ResponseEntity<?> getUserOfStudyGroup(@PathVariable String studyGroupName, @PathVariable String userId) {
        if (userId.contains("=")) {
            userId = new String(Base64.getDecoder().decode(userId), StandardCharsets.UTF_8);
        }
        System.out.println("Decoded userId: " + userId);

        Optional<User> user = studyGroupService.getUserOfStudyGroup(studyGroupName, userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found in the study group: " + studyGroupName);
        }
        return ResponseEntity.ok(user.get());
    }





}
