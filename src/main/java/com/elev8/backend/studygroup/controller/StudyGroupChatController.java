package com.elev8.backend.studygroup.controller;

import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.collegechat.payload.MessageRequest;
import com.elev8.backend.studygroup.model.StudyGroup;
import com.elev8.backend.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class StudyGroupChatController {
    public final StudyGroupRepository studyGroupRepository;

    @MessageMapping("/studyGroup/sendMessage/{studyGroupName}")
    @SendTo("/api/v1/topic/studyGroup/{studyGroupName}")
    public Message sendMessage(@DestinationVariable String studyGroupName , @RequestBody MessageRequest request) {

        StudyGroup studyGroup = studyGroupRepository.findByStudyGroupName(studyGroupName);

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimestamp(LocalDateTime.now());

        if(studyGroup != null) {
            studyGroup.getMessages().add(message);
            studyGroupRepository.save(studyGroup);
        }
        else{
            throw new RuntimeException("StudyGroup not found");
        }
        return message;
    }


}
