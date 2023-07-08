package com.Han2m.portLogistics.service;

import com.Han2m.portLogistics.dto.faceDto;
import com.Han2m.portLogistics.entity.FileEntity;
import com.Han2m.portLogistics.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void save(faceDto faceDto) {
        FileEntity fileEntity = new FileEntity(faceDto.getTitle(), faceDto.getUrl());
        fileRepository.save(fileEntity);
    }

    public List<FileEntity> getFiles() {
        List<FileEntity> all = fileRepository.findAll();
        return all;
    }
}