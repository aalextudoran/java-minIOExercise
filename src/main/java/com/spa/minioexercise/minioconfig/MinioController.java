package com.spa.minioexercise.minioconfig;

import com.spa.minioexercise.dto.DownloadRequest;
import com.spa.minioexercise.dto.SearchRequest;
import com.spa.minioexercise.service.DownloadLogService;
import com.spa.minioexercise.service.SearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/minio")
public class MinioController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private SearchLogService searchLogService;
    @Autowired
    private DownloadLogService downloadLogService;

    @GetMapping("/objects")
    public ResponseEntity<List<String>> listObjects(@RequestParam(value = "continue", required = false) String searchTerm) {
        try {
            List<String> objects = minioService.listObjects();

            if (searchTerm != null && !searchTerm.isEmpty()) {
                objects = objects.stream()
                        .filter(objectName -> objectName.contains(searchTerm))
                        .collect(Collectors.toList());
            }

            return ResponseEntity.ok(objects);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/objects/download/{objectName}")
    public ResponseEntity<byte[]> downloadObject(@PathVariable String objectName) {
        try {
            InputStream stream = minioService.downloadObject(objectName);
            byte[] content = stream.readAllBytes();
            stream.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", objectName);

            return ResponseEntity.ok().headers(headers).body(content);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/objects/upload")
    public ResponseEntity<String> uploadObject(@RequestParam("file") MultipartFile file) {
        try {
            String objectName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            long size = file.getSize();
            String contentType = file.getContentType();

            minioService.uploadObject(objectName, inputStream, size, contentType);

            return ResponseEntity.ok("Upload successful");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed");
        }
    }

    @PostMapping("/objects/sort")
    public ResponseEntity<List<String>> sortObjects(@RequestBody SortRequest sortRequest) {
        try {
            List<String> sortedObjects = minioService.listObjects().stream()
                    .sorted((o1, o2) -> {
                        if ("asc".equalsIgnoreCase(sortRequest.getOrder())) {
                            return o1.compareToIgnoreCase(o2);
                        } else if ("desc".equalsIgnoreCase(sortRequest.getOrder())) {
                            return o2.compareToIgnoreCase(o1);
                        } else {
                            return 0; // Or handle invalid order gracefully
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(sortedObjects);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("objects/search-log")
    public ResponseEntity<String> logSearch(@RequestBody SearchRequest searchRequest) {
        try {
            searchLogService.saveSearchLog(searchRequest.getSearchTerm());
            return ResponseEntity.ok("Search logged successfully");
        } catch (Exception e) {
            return ResponseEntity.ok("Search logged successfully");
        }
    }

    @PostMapping("objects/download-log")
    public ResponseEntity<String> logDownload(@RequestBody DownloadRequest downloadRequest) {
        try {
            downloadLogService.saveDownloadLog(downloadRequest.getObjectName());
            return ResponseEntity.ok("Download logged successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to log Download");
        }
    }

}
