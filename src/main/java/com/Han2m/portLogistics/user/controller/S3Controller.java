package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.faceDto;
import com.Han2m.portLogistics.user.entity.FileEntity;
import com.Han2m.portLogistics.user.service.FileService;
import com.Han2m.portLogistics.user.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

        faceDto faceDto = new faceDto();
        faceDto.setUrl(url);
        fileService.save(faceDto);

        return ResponseEntity.ok(url);
    }
}
