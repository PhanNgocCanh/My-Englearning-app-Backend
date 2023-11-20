package com.exerciseapp.myapp.web.rest;

import com.exerciseapp.myapp.common.enumeration.DocumentType;
import com.exerciseapp.myapp.service.DocumentService;
import com.exerciseapp.myapp.service.FileService;
import com.exerciseapp.myapp.service.dto.DocumentDTO;
import com.exerciseapp.myapp.service.dto.NodeDataDTO;
import com.exerciseapp.myapp.service.response.ApiResponse;
import java.util.Map;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private final DocumentService documentService;
    private final FileService fileService;

    public DocumentController(FileService fileService, DocumentService documentService) {
        this.fileService = fileService;
        this.documentService = documentService;
    }

    @GetMapping("/get-document-tree")
    public ResponseEntity<ApiResponse<NodeDataDTO>> getDocumentTree(@RequestParam("lessonId") String lessonId) {
        return ResponseEntity.ok(ApiResponse.ok(this.documentService.getDocumentTree(lessonId)));
    }

    @PostMapping("/upload-document")
    public String uploadDocument(@RequestParam("file") MultipartFile multipartFile) {
        String path = this.fileService.upload(multipartFile, "documents", false);
        return path;
    }

    @PostMapping("/save-file")
    public ResponseEntity<ApiResponse<Void>> replaceDocument(
        @RequestParam("file") MultipartFile file,
        @RequestParam("documentId") String documentId,
        @RequestParam("lastViewedPage") int pageNumber
    ) {
        this.documentService.saveDocumentFile(file, documentId, pageNumber);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/download-document")
    public ResponseEntity<ByteArrayResource> downloadDocument(@RequestParam("documentId") String documentId) {
        Map<String, byte[]> res = this.documentService.downloadDocument(documentId);
        byte[] data = res.get("data");
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
            .ok()
            .contentLength(data.length)
            .header("Content-type", "application/octet-stream")
            .header("Content-disposition", "attachment; filename=\"data.pdf\"")
            .body(resource);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DocumentDTO>> createDocument(@RequestBody DocumentDTO documentDTO) {
        DocumentDTO response = this.documentService.create(documentDTO);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(@RequestParam("documentId") String documentId) {
        this.documentService.deleteDocument(documentId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
