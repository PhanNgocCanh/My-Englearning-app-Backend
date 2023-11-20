package com.exerciseapp.myapp.service;

import com.exerciseapp.myapp.service.dto.DocumentDTO;
import com.exerciseapp.myapp.service.dto.NodeDataDTO;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    NodeDataDTO getDocumentTree(String lessonId);
    Map<String, byte[]> downloadDocument(String documentId);
    DocumentDTO create(DocumentDTO documentDTO);
    void saveDocumentFile(MultipartFile file, String documentId, int pageNumber);
    void deleteDocument(String documentId);
}
