package com.example.marketclone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Slf4j // 스프링 부트에서 로그를 남기는 방법 중 가장 편하게 사용되는 어노테이션
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${aws.s3.image.bucket}")
    public String bucket;  // S3 버킷 이름

    // 과정
    // 1. 전달받은 파일을 서버에 저장, 즉 업로드를 위한 임시 파일 저장
    // 2. S3에 서버의 임시 파일 업로드
    // 3. 서버의 임시 파일 삭제
    // 4. S3에 저장된 파일 이름과 주소 반환
    public HashMap<String, String> upload(MultipartFile multipartFile, String dirName) throws IOException {

        // 1. 서버에 임시 파일 저장

        // 1-1 파일 객체 생성 (경로는 현재 프로젝트가 위치하는 곳으로)
        File tempFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        // 1-2 파일 존재여부 체크, 없으면 빈 파일 생성
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }

        // 1-3 tempFile에 내용을 작성할 수 있는 FileOutputStream 생성
            // fileOutPutStream의 'append 디폴트 값이 false'이므로 이미 존재하는 파일이라면 기존 내용이 지워진다.
        FileOutputStream fos = new FileOutputStream(tempFile);

        // 1-4 가져온 multipartFile의 내용으로 채워넣기
        fos.write(multipartFile.getBytes());

        // 1-5 다 사용한 FileOutputStream 닫기
        fos.close();


        // 2. S3에 파일 업로드

        // S3로 파일 업로드하기 시작

        String fileName = dirName + "/" + UUID.randomUUID() + tempFile.getName();   // S3에 저장된 파일 이름
        // s3로 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, tempFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

        // 3. 임시 파일 삭제

        if (tempFile.delete()) {
            log.info("File delete success");
        } else {
            log.info("File delete fail");
        }

        // 4. S3에 저장된 파일 이름과 주소 반환

        // 해시맵으로 반환
        HashMap<String, String> imgInfo = new HashMap<>();
        imgInfo.put("fileName", fileName);
        imgInfo.put("img",uploadImageUrl);
        return imgInfo;

    }


    // 파일 삭제하기
    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
