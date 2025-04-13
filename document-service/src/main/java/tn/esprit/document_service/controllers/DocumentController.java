package tn.esprit.document_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tn.esprit.document_service.entities.DocumentEntity;
import tn.esprit.document_service.services.DocumentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<DocumentEntity> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public DocumentEntity getDocumentById(@PathVariable String id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping
    public DocumentEntity createDocument(@RequestBody Map<String, Object> request) {
        DocumentEntity document = new DocumentEntity();
        document.setTitle((String) request.get("title"));

        // Initialize with empty delta if not provided
        if (request.containsKey("delta")) {
            document.setContent((Map<String, Object>) request.get("delta"));
        } else {
            Map<String, Object> emptyDelta = new HashMap<>();
            emptyDelta.put("ops", new ArrayList<>());
            document.setContent(emptyDelta);
        }

        return documentService.createDocument(document);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable String id, @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(documentService.updateDocument(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }
}
