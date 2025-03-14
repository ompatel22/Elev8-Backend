package com.elev8.backend.studygroup.service;
import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.registration.model.User;
import com.elev8.backend.registration.repository.UserRepository;
import com.elev8.backend.registration.service.UserService;
import com.elev8.backend.studygroup.model.StudyGroup;
import com.elev8.backend.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public StudyGroup createStudyGroup(String studyGroup , String description , String userId , String imageUrl) {
        System.out.println("Inside createStudyGroup method");
        System.out.println(imageUrl);

        if(studyGroupRepository.findByStudyGroupName(studyGroup) != null) {
            throw new RuntimeException("Study Group name already exists");
        }
        StudyGroup studyGroupEntity = new StudyGroup();
        studyGroupEntity.setStudyGroupName(studyGroup);
        studyGroupEntity.setStudyGroupDescription(description);
        studyGroupEntity.setOwnerId(userId);
        studyGroupEntity.setImageUrl(imageUrl);
        List<String> usersId = new ArrayList<>();
        usersId.add(userId);
        studyGroupEntity.setMembersId(usersId);
        studyGroupRepository.save(studyGroupEntity);
        return studyGroupEntity;
    }

    public StudyGroup joinStudyGroup(java.lang.String studyGroupName, java.lang.String userId) {

        StudyGroup studyGroup = studyGroupRepository.findByStudyGroupName(studyGroupName);

        if (studyGroup == null) {
            throw new RuntimeException("Study Group not found");
        }

        if  (studyGroup.getMembersId().stream().anyMatch(memberId -> memberId.equals(userId))) {
            throw new RuntimeException("User is already a member of this study group");
        }
        List<java.lang.String> usersId = studyGroup.getMembersId();
        usersId.add(userId);
        studyGroup.setMembersId(usersId);
        studyGroupRepository.save(studyGroup);

        return studyGroup;
    }

    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupRepository.findAll();
    }

    public List<Message> getAllMessagesOfAStudyGroup(java.lang.String studyGroupName) {
        StudyGroup studyGroupEntity = studyGroupRepository.findByStudyGroupName(studyGroupName);
        if (studyGroupEntity == null) {
            throw new RuntimeException("Study Group not found");
        }
        return studyGroupEntity.getMessages();
    }

    public List<User> getAllUsersOfStudyGroup(java.lang.String studyGroupName) {
        StudyGroup studyGroupEntity = studyGroupRepository.findByStudyGroupName(studyGroupName);
        if (studyGroupEntity == null) {
            throw new RuntimeException("Study Group not found");
        }
        List<java.lang.String> usersId = studyGroupEntity.getMembersId();
        return userRepository.findAllById(usersId);
    }

    public User getOwnerOfStudyGroup(java.lang.String studyGroupName) {
        StudyGroup studyGroupEntity = studyGroupRepository.findByStudyGroupName(studyGroupName);
        if (studyGroupEntity == null) {
            throw new RuntimeException("Study Group not found");
        }
        java.lang.String userId = studyGroupEntity.getOwnerId();
        return userRepository.findById(userId).get();
    }

    public boolean deleteStudyGroup(java.lang.String studyGroupName, java.lang.String userId) {
        StudyGroup studyGroupEntity = studyGroupRepository.findByStudyGroupName(studyGroupName);
        if (studyGroupEntity == null) {
            throw new RuntimeException("Study Group not found");
        }
        if(userId.equals(studyGroupEntity.getOwnerId())) {
            studyGroupRepository.delete(studyGroupEntity);
            return true;
        }
        return false;
    }

    public boolean removeStudyGroupMember(java.lang.String studyGroupName, java.lang.String userId) {
        StudyGroup studyGroupEntity = studyGroupRepository.findByStudyGroupName(studyGroupName);
        if (studyGroupEntity == null) {
            throw new RuntimeException("Study Group not found");
        }
        List<java.lang.String> usersIds = studyGroupEntity.getMembersId();
        if(usersIds.contains(userId)) {
            usersIds.remove(userId);
            studyGroupEntity.setMembersId(usersIds);
            studyGroupRepository.save(studyGroupEntity);
            return true;
        }
        return false;
    }
    public Optional<User> getUserOfStudyGroup(java.lang.String studyGroupName, java.lang.String userId) {
        // Decode userId if it's Base64-encoded
        if (userId.contains("=")) {
            userId = new java.lang.String(Base64.getDecoder().decode(userId), StandardCharsets.UTF_8);
        }

        StudyGroup studyGroupEntity = studyGroupRepository.findByStudyGroupName(studyGroupName);
        if (studyGroupEntity == null) {
            return Optional.empty();
        }

        java.lang.String finalUserId = userId;
        Optional<java.lang.String> membersId = studyGroupEntity.getMembersId()
                .stream()
                .filter(memberId -> memberId.equals(finalUserId))
                .findFirst();

        if (membersId.isPresent()) {
            return userService.getUserById(userId);
        }
        return Optional.empty();
    }
    public Optional<StudyGroup> getStudyGroupDetail(java.lang.String studyGroupName) {
        StudyGroup studyGroupEntity = studyGroupRepository.findByStudyGroupName(studyGroupName);
        if (studyGroupEntity == null) {
            return Optional.empty();
        }
        return Optional.of(studyGroupEntity);
    }
}
