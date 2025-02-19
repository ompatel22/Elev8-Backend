package com.elev8.backend.studygroup.service;

import com.elev8.backend.registration.model.User;
import com.elev8.backend.studygroup.model.StudyGroup;
import com.elev8.backend.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;

    public StudyGroup createStudyGroup(String studyGroup , String description ,User user ) {
        System.out.println("Inside createStudyGroup method");

        if(studyGroupRepository.findByStudyGroupName(studyGroup) != null) {
            throw new RuntimeException("Study Group name already exists");
        }
        StudyGroup studyGroupEntity = new StudyGroup();
        studyGroupEntity.setStudyGroupName(studyGroup);
        studyGroupEntity.setStudyGroupDescription(description);
        studyGroupEntity.setOwner(user);
        List<User> users = new ArrayList<>();
        users.add(user);
        studyGroupEntity.setMembers(users);
        studyGroupRepository.save(studyGroupEntity);
        return studyGroupEntity;
    }

    public StudyGroup joinStudyGroup(String studyGroupName, User user) {
        System.out.println("Inside joinStudyGroup method");

        StudyGroup studyGroup = studyGroupRepository.findByStudyGroupName(studyGroupName);

        if (studyGroup == null) {
            throw new RuntimeException("Study Group not found");
        }

        if (studyGroup.getMembers().stream().anyMatch(member -> member.getUsername().equals(user.getUsername()))) {
            throw new RuntimeException("User is already a member of this study group");
        }
        List<User> users = studyGroup.getMembers();
        users.add(user);
        studyGroup.setMembers(users);
        studyGroupRepository.save(studyGroup);

        return studyGroup;
    }

    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupRepository.findAll();
    }
}
