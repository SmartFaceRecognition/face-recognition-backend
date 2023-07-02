package com.Han2m.portLogistics.controller;

import com.Han2m.portLogistics.dto.FileDto;
import com.Han2m.portLogistics.entity.FileEntity;
import com.Han2m.portLogistics.service.FileService;
import com.Han2m.portLogistics.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;
    private final FileService fileService;

    @GetMapping("/api/files")
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        List<FileEntity> fileList = fileService.getFiles();
        return ResponseEntity.ok(fileList);
    }

    @PostMapping("/api/files")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String url = s3Service.uploadFile(file);

        FileDto fileDto = new FileDto();
        fileDto.setUrl(url);
        fileService.save(fileDto);

        return ResponseEntity.ok(url);
    }
}
