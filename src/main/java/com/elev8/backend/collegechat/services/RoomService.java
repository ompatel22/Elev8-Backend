package com.elev8.backend.collegechat.services;

import com.elev8.backend.collegechat.model.Room;
import com.elev8.backend.collegechat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    public void createRoom(String collegeName) {
        Room room = new Room();
            room.setRoomId(collegeName);
            this.roomRepository.save(room);
    }
}
