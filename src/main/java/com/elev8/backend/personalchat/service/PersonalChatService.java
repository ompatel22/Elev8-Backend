package com.elev8.backend.personalchat.service;

import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.personalchat.model.PersonalChat;
import com.elev8.backend.personalchat.repository.PersonalChatRepository;
import com.elev8.backend.registration.model.User;
import com.elev8.backend.registration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonalChatService {
    private final PersonalChatRepository personalChatRepository;
    private final UserRepository userRepository;
    public PersonalChat createOrGetPersonalChat(String member1Id, String member2Id) {
        Optional<PersonalChat> existingChat = personalChatRepository.findByMemberIds(member1Id, member2Id);
        if (existingChat.isPresent()) {
            return existingChat.get();
        }

        User user1 = userRepository.findById(member1Id)
                .orElseThrow(() -> new RuntimeException("User not found: " + member1Id));
        User user2 = userRepository.findById(member2Id)
                .orElseThrow(() -> new RuntimeException("User not found: " + member2Id));

        PersonalChat personalChat = new PersonalChat();
        personalChat.setMember1Id(user1.getId());
        personalChat.setMember2Id(user2.getId());

        return personalChatRepository.save(personalChat);
    }

    public List<Message> getPersonalChatMessages(String member1Id, String member2Id) {
        return personalChatRepository.findByMemberIds(member1Id, member2Id)
                .map(PersonalChat::getMessages)
                .orElse(Collections.emptyList());
    }

    public List<PersonalChat> getPersonalChatsOfaMember(String memberId){
            List<PersonalChat> personalChats = personalChatRepository.findByMember1IdOrMember2Id(memberId, memberId);
            return personalChats;
    }
}
