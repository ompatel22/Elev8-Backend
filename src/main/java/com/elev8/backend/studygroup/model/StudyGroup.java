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
    private String study_group_name;
    private String study_group_description;
    private List<Message> messages = new ArrayList<>();
    private User owner;
    private List<User> members = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudy_group_name() {
        return study_group_name;
    }

    public void setStudy_group_name(String study_group_name) {
        this.study_group_name = study_group_name;
    }

    public String getStudy_group_description() {
        return study_group_description;
    }

    public void setStudy_group_description(String study_group_description) {
        this.study_group_description = study_group_description;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
