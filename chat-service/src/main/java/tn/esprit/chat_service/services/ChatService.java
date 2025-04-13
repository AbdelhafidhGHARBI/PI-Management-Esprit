package tn.esprit.chat_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.chat_service.entities.ChatMessage;
import tn.esprit.chat_service.repositories.ChatMessageRepository;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(new Date());
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessagesForUser(String userId) {
        return chatMessageRepository.findBySenderIdOrReceiverId(userId, userId);
    }
}
