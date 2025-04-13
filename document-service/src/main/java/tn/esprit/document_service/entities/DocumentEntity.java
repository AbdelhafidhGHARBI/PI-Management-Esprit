package tn.esprit.document_service.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@Document(collection = "documents")
public class DocumentEntity {
    @Id
    private String id;
    private String title;
    private Map<String, Object> content; // Latest Quill delta
    private List<DeltaHistory> history;
    private long lastUpdated;

    public DocumentEntity() {
        // Initialize content as an empty delta
        this.content = new HashMap<>();
        this.content.put("ops", new ArrayList<>());
        this.history = new ArrayList<>();
        this.lastUpdated = System.currentTimeMillis();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeltaHistory {
        private Map<String, Object> delta = new HashMap<>();
        private String author;
        private long timestamp;
    }
}
