package com.elev8.backend.studygroup.controller;
import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.registration.model.User;
import com.elev8.backend.studygroup.dto.CreateStudyGroupDTO;
import com.elev8.backend.studygroup.dto.JoinStudyGroupDTO;
import com.elev8.backend.studygroup.model.StudyGroup;
import com.elev8.backend.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/study_group")
@CrossOrigin(origins = "*")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;

    @PostMapping("/create_study_group")
    public ResponseEntity<StudyGroup> createStudyGroup(@RequestBody CreateStudyGroupDTO createStudyGroupDTO) {
        try{
            StudyGroup studyGroup = this.studyGroupService.createStudyGroup(createStudyGroupDTO.getStudyGroupName(), createStudyGroupDTO.getStudyGroupDescription(), createStudyGroupDTO.getOwner());
            return ResponseEntity.ok(studyGroup);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{study_group_name}/join_study_group")
    public ResponseEntity<StudyGroup> joinStudyGroup(@PathVariable String study_group_name ,@RequestBody User user) {
        try{
            System.out.println("Hello");
            StudyGroup studyGroup = this.studyGroupService.joinStudyGroup(study_group_name, user);
            return ResponseEntity.ok(studyGroup);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
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
        List<User> users = this.studyGroupService.getAllUsersOfStudyGroup(study_group_name);
        return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{studyGroupName}/user/{username}")
    public ResponseEntity<?> getUserOfStudyGroup(@PathVariable String studyGroupName, @PathVariable String username) {
        Optional<User> user = studyGroupService.getUserOfStudyGroup(studyGroupName, username);

        if (user.isEmpty()) {
            return ResponseEntity
                    .badRequest() // Change from NOT_FOUND (404) to BAD_REQUEST (400)
                    .body("User not found in the study group: " + studyGroupName);
        }

        return ResponseEntity.ok(user.get());
    }



}
