package tn.esprit.chat_service.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "chats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    private String id;
    private String senderId;  // Sender
    private String receiverId; // Receiver (null if group chat)
    private String message;
    private String messageType; // TEXT, IMAGE, VIDEO, AUDIO
    private Date timestamp;
}
