package com.exerciseapp.myapp.service.impl;

import com.exerciseapp.myapp.common.constants.ErrorConstant;
import com.exerciseapp.myapp.common.enumeration.DocumentType;
import com.exerciseapp.myapp.common.exception.CommonException;
import com.exerciseapp.myapp.domain.Document;
import com.exerciseapp.myapp.repository.DocumentRepository;
import com.exerciseapp.myapp.service.DocumentService;
import com.exerciseapp.myapp.service.FileService;
import com.exerciseapp.myapp.service.dto.DocumentDTO;
import com.exerciseapp.myapp.service.dto.NodeDataDTO;
import com.exerciseapp.myapp.service.mapper.DocumentMapper;
import io.minio.MinioClient;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final FileService fileService;
    private final MinioClient minioClient;

    @Value("${minio.default-bucket}")
    private String defaultBucket;

    public DocumentServiceImpl(
        DocumentMapper documentMapper,
        MinioClient minioClient,
        FileService fileService,
        DocumentRepository documentRepository
    ) {
        this.documentMapper = documentMapper;
        this.minioClient = minioClient;
        this.fileService = fileService;
        this.documentRepository = documentRepository;
    }

    @Override
    public NodeDataDTO getDocumentTree(String lessonId) {
        try {
            Map<String, NodeDataDTO> treeMap = new HashMap<>();
            List<Object[]> objTree = this.documentRepository.getTreeDocument(lessonId);
            List<NodeDataDTO> documentNodeTree = mapToNode(objTree);
            String rootId = UUID.randomUUID().toString();
            NodeDataDTO root = new NodeDataDTO(rootId, null, "root", null, null, null, 0);
            treeMap.put(rootId, root);
            documentNodeTree.forEach(document -> createTree(rootId, treeMap, document));

            return root;
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }

    private void createTree(String rootId, Map<String, NodeDataDTO> treeMap, NodeDataDTO node) {
        if (StringUtils.isEmpty(node.getParentId())) {
            node.setParentId(rootId);
            treeMap.get(rootId).addChildren(node);
            treeMap.put(node.getDocumentNodeId(), node);
        } else if (treeMap.containsKey(node.getParentId())) {
            treeMap.get(node.getParentId()).addChildren(node);
            treeMap.put(node.getDocumentNodeId(), node);
        }
    }

    private List<NodeDataDTO> mapToNode(List<Object[]> obj) {
        return obj
            .parallelStream()
            .map(o ->
                new NodeDataDTO(
                    (String) o[0], // documentId
                    (String) o[1], // parentId
                    (String) o[2], // documentName
                    (String) o[3], // documentType
                    (String) o[4], // path
                    (String) o[5], // color
                    (int) o[6] // last page viewed
                )
            )
            .collect(Collectors.toList());
    }

    @Override
    public Map<String, byte[]> downloadDocument(String documentId) {
        try {
            Optional<Document> document = documentRepository.findById(documentId);
            if (document.isEmpty()) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.DOCUMENT_NOT_FOUND);
            }

            return this.fileService.download(document.get().getPath());
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }

    @Override
    public DocumentDTO create(DocumentDTO documentDTO) {
        try {
            Document document = documentMapper.toEntity(documentDTO);
            if (DocumentType.FILE.name().equals(documentDTO.getDocumentType())) {
                document.setDocumentType(DocumentType.FILE.name());
            } else {
                document.setDocumentType(DocumentType.FOLDER.name());
            }
            Document savedDocument = documentRepository.save(document);

            return documentMapper.toDTO(savedDocument);
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }

    @Override
    public void saveDocumentFile(MultipartFile file, String documentId, int pageNumber) {
        try {
            Optional<Document> documentOpt = documentRepository.findById(documentId);
            if (documentOpt.isEmpty()) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.DOCUMENT_NOT_FOUND);
            }
            Document document = documentOpt.get();
            String documentPath = document.getPath();
            document.setLastViewedPage(pageNumber);
            this.fileService.replaceFile(file, documentPath);
            this.documentRepository.save(document);
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteDocument(String documentId) {
        try {
            Optional<Document> documentOpt = this.documentRepository.findById(documentId);
            if (documentOpt.isEmpty()) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.DOCUMENT_NOT_FOUND);
            }
            if (DocumentType.FILE.name().equals(documentOpt.get().getDocumentType())) {
                this.fileService.deleteFile(documentOpt.get().getPath());
            }
            List<Document> documents = this.documentRepository.getAllByParentId(documentId);
            documents
                .parallelStream()
                .forEach(document -> {
                    if (DocumentType.FILE.name().equals(document.getDocumentType())) {
                        this.fileService.deleteFile(document.getPath());
                    }
                });
            this.documentRepository.deleteDocumentByDocumentId(documentId);
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }
}
