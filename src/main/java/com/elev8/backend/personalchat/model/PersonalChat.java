package com.elev8.backend.personalchat.model;

import com.elev8.backend.collegechat.model.Message;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "personal_chat")
public class PersonalChat {
    @Id
    private String id;
    private String member1Id;
    private String member2Id;
    private List<Message> messages;
}
