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
    private java.lang.String id;
    private java.lang.String roomId;
    private List<Message> messages = new ArrayList<>();
    private List<String> usersId = new ArrayList<>();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(java.lang.String roomId) {
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public List<String> getUsersId() {return usersId;}

    public void setUsersId(List<String> usersId) {this.usersId = usersId;}
}
