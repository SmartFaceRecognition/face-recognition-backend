package com.Han2m.portLogistics.user.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    public String uploadFaceImg(MultipartFile multipartFile,Long key) throws IOException {

        String fileName = multipartFile.getOriginalFilename();

        //파일 형식 구하기
        assert fileName != null;
        String ext = fileName.split("\\.")[1];
        String contentType = switch (ext) {
            case "jpeg" -> "image/jpeg";
            case "jpg" -> "image/jpg";
            case "png" -> "image/png";
            default -> "";

            //content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되서 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨.
        };

        try {
            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(bytes.length);

            amazonS3.putObject(new PutObjectRequest(bucket, "faceImg/"+key.toString(), multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        return amazonS3.getUrl(bucket, "faceImg/"+key.toString()).toString();
    }

}