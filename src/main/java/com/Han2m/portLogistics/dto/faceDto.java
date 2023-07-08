package com.Han2m.portLogistics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class faceDto {
    private String title;
    private String url;
    private MultipartFile file;
}
