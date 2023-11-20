package com.exerciseapp.myapp.service;

import java.util.LinkedHashMap;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile file, String path, boolean isReplace);

    void replaceFile(MultipartFile file, String path);

    LinkedHashMap<String, byte[]> download(String path);

    void deleteFile(String path);
}
