package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.FaceDto;
import com.Han2m.portLogistics.user.entity.FileEntity;
import com.Han2m.portLogistics.user.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void save(FaceDto faceDto) {
        FileEntity fileEntity = new FileEntity(faceDto.getTitle(), faceDto.getUrl());
        fileRepository.save(fileEntity);
    }

    public List<FileEntity> getFiles() {
        List<FileEntity> all = fileRepository.findAll();
        return all;
    }
}