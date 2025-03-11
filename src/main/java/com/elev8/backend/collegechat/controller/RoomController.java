package com.elev8.backend.collegechat.controller;

import com.elev8.backend.collegechat.model.Message;
import com.elev8.backend.collegechat.model.Room;
import com.elev8.backend.collegechat.repository.RoomRepository;
import com.elev8.backend.registration.model.User;
import com.elev8.backend.registration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("*")
public class RoomController {

    private final RoomRepository roomRepository;
    private final UserService userService;


    // Create room
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId, @RequestBody String userId) {

        if (roomRepository.findByRoomId(roomId) != null) {
            return ResponseEntity.badRequest().body("Room already exists");
        }

        Room room = new Room();
        room.setRoomId(roomId);
        List<String> users = room.getUsersId();
        users.add(userId);
        room.setUsersId(users);
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    // Get room
    @GetMapping("/chat")
    public ResponseEntity<?> joinRoom(@RequestHeader("username") java.lang.String username) {
        java.lang.String collegeName = this.userService.getUserByUsername(username).getCollegeName();
        System.out.println(username);
        Room room = roomRepository.findByRoomId(collegeName);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found");
        }

        return ResponseEntity.ok(room);
    }

    // Get messages of room with pagination
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable java.lang.String roomId,
                                                     @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                     @RequestParam(value = "size", defaultValue = "20", required = false) int size) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Message> messages = room.getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);

        List<Message> paginatedMessages = messages.subList(start, end);
        return ResponseEntity.ok(paginatedMessages);
    }

    @GetMapping("/{RoomId}/users")
    public ResponseEntity<List<User>> getUsers(@PathVariable String RoomId) {
        Room room = roomRepository.findByRoomId(RoomId);
        if (room == null) {
            return ResponseEntity.badRequest().build();
        }
        List<String> usersId = room.getUsersId();
        System.out.println(usersId);
        List<User> users = userService.getAllUsersById(usersId);
        return ResponseEntity.ok(users);
    }
}
