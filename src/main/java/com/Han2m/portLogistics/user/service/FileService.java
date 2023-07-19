package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.FaceDto;
import com.Han2m.portLogistics.user.entity.File;
import com.Han2m.portLogistics.user.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void save(FaceDto faceDto) {
        File file = new File(faceDto.getTitle(), faceDto.getUrl());
        fileRepository.save(file);
    }

    public List<File> getFiles() {
        List<File> all = fileRepository.findAll();
        return all;
    }
}