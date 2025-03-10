package com.elev8.backend.personalchat.controller;

import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.collegechat.payload.MessageRequest;
import com.elev8.backend.personalchat.model.PersonalChat;
import com.elev8.backend.personalchat.repository.PersonalChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class PersonalChatChatController {
    private final PersonalChatRepository personalChatRepository;

    @MessageMapping("/personal_chat/send_message/{member1Id}/{member2Id}")
    @SendTo("/api/v1/topic/personal_chat/{member1Id}/{member2Id}")
    public Message sendMessage(@PathVariable("member1Id") String member1Id, @PathVariable("member2Id") String member2Id, @RequestBody MessageRequest messageRequest) {
        Optional<PersonalChat> personalChat = personalChatRepository.findByMemberIds(member1Id,member2Id);
        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setSender(messageRequest.getSender());
        if (personalChat.isPresent()) {
            personalChat.get().getMessages().add(message);
            personalChatRepository.save(personalChat.get());
        }
        else{
            throw new RuntimeException("personal chat not found");
        }
        return message;
    }
}
