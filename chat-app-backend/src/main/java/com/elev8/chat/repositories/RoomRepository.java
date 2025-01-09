package com.elev8.chat.repositories;

import com.elev8.chat.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String>{
    Room findByRoomId(String roomId);
}
