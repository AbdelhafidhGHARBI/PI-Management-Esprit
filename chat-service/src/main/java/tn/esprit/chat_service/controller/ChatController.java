package tn.esprit.chat_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.chat_service.entities.ChatMessage;
import tn.esprit.chat_service.services.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage chatMessage) {
        return chatService.saveMessage(chatMessage);
    }

    @GetMapping("/messages/{userId}")
    public List<ChatMessage> getMessages(@PathVariable String userId) {
        return chatService.getMessagesForUser(userId);
    }
}
