package com.spa.minioexercise.dao;

import com.spa.minioexercise.entity.SearchLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchLogsRepository extends JpaRepository <SearchLogs, Integer> {
}
