package com.elev8.backend.studygroup.model;

import com.elev8.backend.collegechat.model.Message;
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
}