package com.exerciseapp.myapp.config;

import com.exerciseapp.myapp.common.exception.CommonException;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        try {
            return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
        } catch (Exception e) {
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
