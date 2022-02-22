package com.example.marketclone.controller;

import com.example.marketclone.security.UserDetailsImpl;
import com.example.marketclone.service.CommentService;
import com.example.marketclone.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final S3Uploader s3Uploader;

    // 사진 업로드 테스트
    @PostMapping("/image")
    public String uploadimage(@RequestParam("img") MultipartFile img) throws IOException {
        return s3Uploader.upload(img, "review");
    }


    // 포토 리뷰 작성 API
    @PostMapping("/articles")
    public void createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("img") MultipartFile img) {
        commentService.createComment(userDetails.getUser(), title, content, img);
    }
}
