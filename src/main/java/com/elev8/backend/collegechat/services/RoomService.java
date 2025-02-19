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
    public void createRoom(String collegeName, User user) {
        Room room = new Room();
            room.setRoomId(collegeName);
            List<User> users = room.getUsers();
            users.add(user);
            room.setUsers(users);
            this.roomRepository.save(room);
    }
    public void joinRoom(String collegeName, User user) {
        Room room = this.roomRepository.findByRoomId(collegeName);
        List<User> users = room.getUsers();
        users.add(user);
        room.setUsers(users);
        this.roomRepository.save(room);
    }
}
