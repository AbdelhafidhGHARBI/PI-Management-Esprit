package tn.esprit.document_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import tn.esprit.document_service.entities.DeltaMessage;
import tn.esprit.document_service.entities.DocumentEntity;
import tn.esprit.document_service.repositories.DocumentRepository;
import tn.esprit.document_service.services.DocumentService;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class DocumentWebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentRepository documentRepository;

    @MessageMapping("/documents/edit/{documentId}")
    public void handleEdit(@DestinationVariable String documentId, DeltaMessage deltaMessage) {
        Optional<DocumentEntity> optional = documentRepository.findById(documentId);

        if (optional.isPresent()) {
            DocumentEntity doc = optional.get();

            // Save delta to history
            DocumentEntity.DeltaHistory entry = new DocumentEntity.DeltaHistory(
                    deltaMessage.getDelta(),
                    deltaMessage.getAuthor(),
                    deltaMessage.getTimestamp()
            );

            if (doc.getHistory() == null) {
                doc.setHistory(new ArrayList<>());
            }
            doc.getHistory().add(entry);

            // Update full content
            doc.setContent(deltaMessage.getFullContent());
            doc.setLastUpdated(System.currentTimeMillis());

            documentRepository.save(doc);

            // Broadcast to all connected clients -/ sender
            messagingTemplate.convertAndSendToUser(
                    deltaMessage.getAuthor(),
                    "/topic/document/" + documentId,
                    deltaMessage
            );
        }
    }
}
