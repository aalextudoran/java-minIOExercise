package com.spa.minioexercise.service;

import com.spa.minioexercise.entity.DownloadLogs;
import com.spa.minioexercise.dao.DownloadLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownloadLogService {

    @Autowired
    private DownloadLogsRepository downloadLogsRepository;

    public void saveDownloadLog(String objectName) {
        DownloadLogs downloadLogs = new DownloadLogs(objectName);
        downloadLogsRepository.save(downloadLogs);
    }
}
