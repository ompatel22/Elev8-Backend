package com.elev8.backend.studygroup.dto;

import com.elev8.backend.registration.model.User;
import java.util.List;

public class StudyGroupWithUsers {
    private String id;
    private String studyGroupName;
    private String studyGroupDescription;
    private String ownerId;
    private List<User> members; // List of users instead of just IDs

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudyGroupName() {
        return studyGroupName;
    }

    public void setStudyGroupName(String studyGroupName) {
        this.studyGroupName = studyGroupName;
    }

    public String getStudyGroupDescription() {
        return studyGroupDescription;
    }

    public void setStudyGroupDescription(String studyGroupDescription) {
        this.studyGroupDescription = studyGroupDescription;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
