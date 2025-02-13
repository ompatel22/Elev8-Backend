package com.elev8.backend.collegechat.repository;

import com.elev8.backend.collegechat.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String>{
    Room findByRoomId(String roomId);
}
