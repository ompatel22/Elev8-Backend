package com.elev8.backend.personalchat.dto;

import com.elev8.backend.collegechat.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class PersonalChatResponseDto {
    private String id;
    private String githubUserName;
    private List<Message> messages;
    public PersonalChatResponseDto(String githubUserName, List<Message> messages,String id) {
        this.githubUserName = githubUserName;
        this.messages = messages;
        this.id = id;
    }
}
