package tn.esprit.document_service.services;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    String uploadFile(MultipartFile file) throws IOException;
    List<String> listFiles();
    Resource downloadFile(String filename) throws IOException;
    void deleteFile(String filename) throws IOException;
}
