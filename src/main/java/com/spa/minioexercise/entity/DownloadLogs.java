package com.spa.minioexercise.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="download_logs")
@Data
public class DownloadLogs {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "object_name")
    private String objectName;


    @Column(name = "download_timestamp")
    @CreationTimestamp
    private LocalDateTime downloadTimestamp;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "download_ip")
    private String downloadIp;

    public DownloadLogs(String objectName) {
        this.objectName = objectName;
        this.downloadTimestamp = LocalDateTime.now();
    }
}


