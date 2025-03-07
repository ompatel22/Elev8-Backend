package com.elev8.backend.studygroup.model;

import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.registration.model.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "study_group")
public class StudyGroup {
    @Id
    private String id;
    private String studyGroupName;
    private String studyGroupDescription;
    private List<Message> messages = new ArrayList<>();
    private String ownerId;
    private List<String> membersId = new ArrayList<>();
    private String imageUrl;

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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getMembersId() {
        return membersId;
    }

    public void setMembersId(List<String> membersId) {
        this.membersId = membersId;
    }
}
