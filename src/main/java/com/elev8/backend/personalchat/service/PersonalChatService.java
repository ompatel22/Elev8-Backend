package com.elev8.backend.personalchat.service;

import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.personalchat.dto.PersonalChatResponseDto;
import com.elev8.backend.personalchat.dto.PersonalChatSingleResponseDto;
import com.elev8.backend.personalchat.model.PersonalChat;
import com.elev8.backend.personalchat.repository.PersonalChatRepository;
import com.elev8.backend.registration.model.User;
import com.elev8.backend.registration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PersonalChatService {
    private final PersonalChatRepository personalChatRepository;
    private final UserRepository userRepository;
    public PersonalChatSingleResponseDto    createOrGetPersonalChat(String member1Id, String member2Id) {
        Optional<PersonalChat> existingChat = personalChatRepository.findByMemberIds(member1Id, member2Id);
        if (existingChat.isPresent()) {
            PersonalChatSingleResponseDto responseDto = new PersonalChatSingleResponseDto();
            Optional<User> userI = userRepository.findById(member1Id);
            Optional<User> userII = userRepository.findById(member2Id);
            responseDto.setMessage(existingChat.get().getMessages());
            responseDto.setMember1Name(userI.get().getGithubUsername());
            responseDto.setMember2Name(userII.get().getGithubUsername());
            return responseDto;
        }

        User user1 = userRepository.findById(member1Id)
                .orElseThrow(() -> new RuntimeException("User not found: " + member1Id));
        User user2 = userRepository.findById(member2Id)
                .orElseThrow(() -> new RuntimeException("User not found: " + member2Id));

        PersonalChat personalChat = new PersonalChat();
        personalChat.setMember1Id(user1.getId());
        personalChat.setMember2Id(user2.getId());
        personalChatRepository.save(personalChat);
        PersonalChatSingleResponseDto personalChatSingleResponseDto = new PersonalChatSingleResponseDto();
        Optional<User> userA = userRepository.findById(personalChat.getMember1Id());
        Optional<User> userB = userRepository.findById(personalChat.getMember2Id());
        if (userA.isPresent() && userB.isPresent()) {
            personalChatSingleResponseDto.setMember1Name(userA.get().getGithubUsername());
            personalChatSingleResponseDto.setMember2Name(userB.get().getGithubUsername());
            personalChatSingleResponseDto.setMessage(personalChat.getMessages());
        }
        return personalChatSingleResponseDto;
    }

    public List<Message> getPersonalChatMessages(String member1Id, String member2Id) {
        return personalChatRepository.findByMemberIds(member1Id, member2Id)
                .map(PersonalChat::getMessages)
                .orElse(Collections.emptyList());
    }

    public List<PersonalChatResponseDto> getPersonalChatsOfaMember(String memberId) {
        List<PersonalChat> personalChats = personalChatRepository.findByMember1IdOrMember2Id(memberId, memberId);
        System.out.println("Fetched personal chats: " + personalChats);

        List<PersonalChatResponseDto> responseList = new ArrayList<>();

        for (PersonalChat chat : personalChats) {
            String otherUserId = chat.getMember1Id().equals(memberId) ? chat.getMember2Id() : chat.getMember1Id();

            if (otherUserId == null) {
                System.err.println("Error: Other User ID is null for chat: " + chat);
                continue;
            }

            System.out.println("Fetching user details for ID: " + otherUserId);

            Optional<User> optionalUser = userRepository.findById(otherUserId);
            if (optionalUser.isEmpty()) {
                System.err.println("Error: User not found for ID: " + otherUserId);
                continue;
            }

            User otherUser = optionalUser.get();
            System.out.println("Found user: " + otherUser);
            responseList.add(new PersonalChatResponseDto(
                    otherUser.getGithubUsername(),
                    chat.getMessages(),
                    otherUser.getId()
            ));
        }
        System.out.println("Final Response List: " + responseList);
        return responseList;
    }

    public List<Message> getAllMessagesOfAPersonalChat(String member1,String member2){
        Optional<PersonalChat> personalChat = personalChatRepository.findByMemberIds(member1,member2);
        if (personalChat.isPresent()) {
            return personalChat.get().getMessages();
        }
        return Collections.emptyList();
    }
}
