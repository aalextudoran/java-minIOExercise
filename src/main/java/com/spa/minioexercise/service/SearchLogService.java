package com.spa.minioexercise.service;

import com.spa.minioexercise.entity.SearchLogs;
import com.spa.minioexercise.dao.SearchLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchLogService {

    @Autowired
    private SearchLogsRepository searchLogsRepository;

    public void saveSearchLog(String searchTerm) {
        SearchLogs searchLog = new SearchLogs(searchTerm);
        searchLogsRepository.save(searchLog);
    }
}
