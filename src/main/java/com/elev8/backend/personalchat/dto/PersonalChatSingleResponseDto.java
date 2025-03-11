package com.elev8.backend.personalchat.dto;

import com.elev8.backend.collegechat.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class PersonalChatSingleResponseDto {
    private String member1Name;
    private String member2Name;
    private List<Message> message;
}
