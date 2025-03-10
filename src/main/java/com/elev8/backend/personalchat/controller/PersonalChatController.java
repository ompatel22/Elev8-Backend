package com.elev8.backend.personalchat.controller;

import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.personalchat.service.PersonalChatService;
import com.elev8.backend.personalchat.model.PersonalChat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/personal_chat")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonalChatController {
    private final PersonalChatService personalChatService;

    @PostMapping("/create_or_get_personal_chat/{member1Id}/{member2Id}")
    public ResponseEntity<?> createOrGetPersonalChat(@PathVariable String member1Id, @PathVariable String member2Id) {
        return ResponseEntity.ok(personalChatService.createOrGetPersonalChat(member1Id, member2Id));
    }

    @GetMapping("/{member1Id}/{member2Id}/messages")
    public ResponseEntity<?> getPersonalChatMessages(@PathVariable String member1Id, @PathVariable String member2Id,@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                     @RequestParam(value = "size", defaultValue = "20", required = false) int size) {
        List<Message> messages = personalChatService.getPersonalChatMessages(member1Id,member2Id);
        if(messages == null || messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/all_personal_chats/{memberId}")
    public ResponseEntity<?> getAllPersonalChatsOfAMember(@PathVariable String memberId) {
        List<PersonalChat> personalChats = personalChatService.getPersonalChatsOfaMember(memberId);
        if(personalChats == null || personalChats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(personalChats);
    }
}
