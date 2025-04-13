package tn.esprit.chat_service.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import tn.esprit.chat_service.entities.ChatMessage;
import tn.esprit.chat_service.services.ChatService;

@Controller
public class ChatWebSocketHandler {
    @Autowired
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage handleChatMessage(ChatMessage chatMessage) {
        return chatService.saveMessage(chatMessage);
    }
}
