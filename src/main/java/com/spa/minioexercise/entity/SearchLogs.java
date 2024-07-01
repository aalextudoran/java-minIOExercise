package com.spa.minioexercise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name="search_logs")
@Getter
@Setter
public class SearchLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String searchTerm;

    @Column(nullable = false)
    private LocalDateTime searchTimestamp;

    public SearchLogs(String searchTerm) {
        this.searchTerm = searchTerm;
        this.searchTimestamp = LocalDateTime.now();
    }

}