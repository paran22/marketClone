package com.example.marketclone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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

    // 파라미터로 multipartFile(업로드하려는 파일)과 dirName(이 파일을 업로드하고 싶은 S3 버킷의 폴더 이름)을 받는다.
    public HashMap<String, String> upload(MultipartFile multipartFile, String dirName) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(MediaType.ALL_VALUE);
        metadata.setContentLength(multipartFile.getSize());

        // 2-2 S3에 저장할 파일명 생성
        // UUID 사용 이유 : 이름이 같은 파일들이 서로 덮어쓰지 않고 구분될 수 있도록
        String uploadImageName = dirName + "/" + UUID.randomUUID() + multipartFile.getOriginalFilename();

        // s3로 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket,uploadImageName, multipartFile.getInputStream(),metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));


        String uploadImageUrl = amazonS3Client.getUrl(bucket, uploadImageName).toString();


        // 4. S3에 저장된 파일 이름과 주소 반환

        // 해시맵으로 반환
        HashMap<String, String> imgInfo = new HashMap<>();
        imgInfo.put("fileName", uploadImageName);
        imgInfo.put("img",uploadImageUrl);
        return imgInfo;





//        // 1. 서버에 임시 파일 저장
//
//        // 1-1 서버에 임시 파일을 저장할 폴더가 없으면 새로 생성
//        File Folder = new File(System.getProperty("user.dir")+"/tempFileFolder"); // '/'으로 적든 '\\'으로 적든 같다.
//        if (!Folder.exists()) {
//            Folder.mkdir();
//        }
//
//        // 1-2 파일 객체 생성 (경로는 현재 프로젝트가 위치하는 곳으로)
//        File tempFile = new File(System.getProperty("user.dir") + "/tempFileFolder/" + multipartFile.getOriginalFilename());
//
//        // 1-3 파일 존재여부 체크, 없으면 빈 파일 생성
//        if (!tempFile.exists()) {
//            tempFile.createNewFile();
//        }
//
//        // 1-4 tempFile에 내용을 작성할 수 있는 FileOutputStream 생성
//            // fileOutPutStream의 'append 디폴트 값이 false'이므로 이미 내용이 존재하는 파일이라면 기존 내용이 지워진다.
//        FileOutputStream fos = new FileOutputStream(tempFile);
//
//        // 1-5 가져온 multipartFile의 내용으로 채워넣기
//        fos.write(multipartFile.getBytes());
//
//        // 1-6 다 사용한 FileOutputStream 닫기
//        fos.close();
//
//
//        // 2. S3에 파일 업로드
//
//        // 2-1 파일명이 너무 길면 자르기 (S3 저장에는 문제가 없지만 나중에 DB에 파일명이 저장될 때 String 길이 제한에 걸릴 수 있다)
//        String tempFileName = tempFile.getName();
////
////        // 확장자는 떼야한다.
////        if (tempFileName.length()>100) {
////            tempFileName = tempFileName.substring(99);
////        }
//
//        // 2-2 S3에 저장할 파일명 생성
//        // UUID 사용 이유 : 이름이 같은 파일들이 서로 덮어쓰지 않고 구분될 수 있도록
//        String uploadImageName = dirName + "/" + UUID.randomUUID() + tempFileName;
//
//        // s3로 업로드
//        amazonS3Client.putObject(bucket, uploadImageName, tempFile);
//
//
//        String uploadImageUrl = amazonS3Client.getUrl(bucket, uploadImageName).toString();
//
//        // 3. 임시 파일 삭제
//        if (tempFile.exists()) {
//            tempFile.delete();
//        }
//
//        // 4. S3에 저장된 파일 이름과 주소 반환
//
//        // 해시맵으로 반환
//        HashMap<String, String> imgInfo = new HashMap<>();
//        imgInfo.put("fileName", uploadImageName);
//        imgInfo.put("img",uploadImageUrl);
//        return imgInfo;

    }



    // 파일 삭제하기
    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
