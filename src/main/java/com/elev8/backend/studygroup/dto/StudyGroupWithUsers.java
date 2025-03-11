package com.elev8.backend.studygroup.dto;

import com.elev8.backend.registration.model.User;
import java.util.List;

public class StudyGroupWithUsers {
    private java.lang.String id;
    private java.lang.String studyGroupName;
    private java.lang.String studyGroupDescription;
    private java.lang.String ownerId;
    private List<User> members; // List of users instead of just IDs

    // Getters and Setters
    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getStudyGroupName() {
        return studyGroupName;
    }

    public void setStudyGroupName(java.lang.String studyGroupName) {
        this.studyGroupName = studyGroupName;
    }

    public java.lang.String getStudyGroupDescription() {
        return studyGroupDescription;
    }

    public void setStudyGroupDescription(java.lang.String studyGroupDescription) {
        this.studyGroupDescription = studyGroupDescription;
    }

    public java.lang.String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(java.lang.String ownerId) {
        this.ownerId = ownerId;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
