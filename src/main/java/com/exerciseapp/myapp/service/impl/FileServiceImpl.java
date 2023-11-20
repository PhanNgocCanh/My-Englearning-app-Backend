package com.exerciseapp.myapp.service.impl;

import com.exerciseapp.myapp.common.constants.ErrorConstant;
import com.exerciseapp.myapp.common.constants.FileExtension;
import com.exerciseapp.myapp.common.exception.CommonException;
import com.exerciseapp.myapp.service.FileService;
import com.exerciseapp.myapp.utils.system.SecurityContextHolder;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.UUID;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    private static final String DATETIME_PATTERN = "yyyyMMdd";
    private final MinioClient minioClient;

    @Value("${minio.default-bucket}")
    private String defaultBucket;

    public FileServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public String upload(MultipartFile file, String pathFile, boolean isReplace) {
        try {
            String userId = SecurityContextHolder.getCurrentUserId();
            String fileName = file.getOriginalFilename();
            if (!FileExtension.PDF_EXTENSION.equals(FileNameUtils.getExtension(fileName)) && !isReplace) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.FILE_FORMAT_INVALID);
            }
            String path = "";
            if (!isReplace) {
                path = pathFile + "/" + userId + "/" + UUID.randomUUID() + "_" + fileName; // create path file for new document
            } else {
                path = pathFile; // replace file with exists document
            }
            // Upload file to Minio
            minioClient.putObject(
                PutObjectArgs.builder().bucket(defaultBucket).object(path).stream(file.getInputStream(), file.getSize(), -1).build()
            );

            return path;
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }

    @Override
    public LinkedHashMap<String, byte[]> download(String path) {
        try {
            InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(defaultBucket).object(path).build());
            LinkedHashMap<String, byte[]> data = new LinkedHashMap<>();
            data.put("data", IOUtils.toByteArray(stream));

            return data;
        } catch (Exception e) {
            if (e.getMessage().contains("NoSuchKey")) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.FILE_NOT_FOUND);
            } else {
                throw CommonException
                    .create(HttpStatus.INTERNAL_SERVER_ERROR)
                    .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                    .message(e.getMessage());
            }
        }
    }

    @Override
    public void replaceFile(MultipartFile file, String path) {
        this.upload(file, path, true);
    }

    @Override
    public void deleteFile(String path) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(defaultBucket).object(path).build();
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }
}
