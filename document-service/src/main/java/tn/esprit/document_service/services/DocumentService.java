package tn.esprit.document_service.services;

import org.springframework.stereotype.Service;
import tn.esprit.document_service.entities.DocumentEntity;
import tn.esprit.document_service.repositories.DocumentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DocumentEntity> getAllDocuments() {
        return documentRepository.findAll();
    }

    public DocumentEntity getDocumentById(String id) {
        return documentRepository.findById(id).get();
    }

    public DocumentEntity createDocument(DocumentEntity document) {
        // Ensure we have an empty delta if content is null
        if (document.getContent() == null) {
            document.setContent(new HashMap<>());
            document.getContent().put("ops", new ArrayList<>());
        }

        document.setLastUpdated(System.currentTimeMillis());
        return documentRepository.save(document);
    }

    // In DocumentService.java
    public DocumentEntity updateDocument(String id, Map<String, Object> body) {
        // Find the document by its ID
        DocumentEntity doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        // Debug logging
        System.out.println("Received update body: " + body);

        // Support both field names for compatibility
        Map<String, Object> content = null;
        if (body.containsKey("fullContent")) {
            content = (Map<String, Object>) body.get("fullContent");
        } else if (body.containsKey("delta")) {
            content = (Map<String, Object>) body.get("delta");
        } else if (body.containsKey("content")) {
            content = (Map<String, Object>) body.get("content");
        }

        if (content != null) {
            // Update document content
            doc.setContent(content);

            // Create a history entry
            DocumentEntity.DeltaHistory deltaHistory = new DocumentEntity.DeltaHistory(
                    content,
                    body.containsKey("author") ? (String) body.get("author") : "anonymous",
                    System.currentTimeMillis()
            );

            if (doc.getHistory() == null) {
                doc.setHistory(new ArrayList<>());
            }
            doc.getHistory().add(deltaHistory);
        }

        // Update last modified timestamp
        doc.setLastUpdated(System.currentTimeMillis());

        // If title is provided, update it
        if (body.containsKey("title")) {
            doc.setTitle((String) body.get("title"));
        }

        // Save the updated document back to the database
        return documentRepository.save(doc);
    }

    public void deleteDocument(String id) {
        documentRepository.deleteById(id);
    }

}
