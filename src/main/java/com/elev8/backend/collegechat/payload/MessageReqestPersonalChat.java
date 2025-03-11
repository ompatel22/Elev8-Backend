package com.elev8.backend.collegechat.payload;

import lombok.Data;

@Data
public class MessageReqestPersonalChat {
    private String sender;
    private String content;
}
