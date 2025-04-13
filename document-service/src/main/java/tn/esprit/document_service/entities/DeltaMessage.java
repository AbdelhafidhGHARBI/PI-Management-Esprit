package tn.esprit.document_service.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DeltaMessage {
    private String documentId;
    private Map<String, Object> delta;
    private Map<String, Object> fullContent;
    private String author;
    private long timestamp;
}
