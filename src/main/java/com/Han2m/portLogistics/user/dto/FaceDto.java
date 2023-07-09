package com.Han2m.portLogistics.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FaceDto {
    private String title;
    private String url;
    private MultipartFile file;
}
