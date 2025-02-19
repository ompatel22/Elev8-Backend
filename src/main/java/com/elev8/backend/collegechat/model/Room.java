package com.elev8.backend.collegechat.model;

import com.elev8.backend.registration.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "rooms")
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String id;
    private String roomId;
    private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getUsers() {return users;}

    public void setUsers(List<User> users) {this.users = users;}
}
