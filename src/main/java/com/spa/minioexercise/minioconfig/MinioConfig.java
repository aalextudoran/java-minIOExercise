package com.spa.minioexercise.minioconfig;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://192.168.1.6:58738")
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
