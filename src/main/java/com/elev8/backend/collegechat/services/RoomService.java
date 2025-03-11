package com.elev8.backend.collegechat.services;

import com.elev8.backend.collegechat.model.Room;
import com.elev8.backend.collegechat.repository.RoomRepository;
import com.elev8.backend.registration.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    public void createRoom(java.lang.String collegeName, String userId) {
        Room room = new Room();
            room.setRoomId(collegeName);
            List<String> users = room.getUsersId();
            users.add(userId);
            room.setUsersId(users);
            this.roomRepository.save(room);
    }
    public void joinRoom(String collegeName, String userId) {
        Room room = this.roomRepository.findByRoomId(collegeName);
        List<String> users = room.getUsersId();
        users.add(userId);
        room.setUsersId(users);
        this.roomRepository.save(room);
    }
}
