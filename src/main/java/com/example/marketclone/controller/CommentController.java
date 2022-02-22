package com.example.marketclone.controller;

import com.example.marketclone.responseDto.CommentResponseDto;
import com.example.marketclone.security.UserDetailsImpl;
import com.example.marketclone.service.CommentService;
import com.example.marketclone.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
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


    // 댓글 조회하기
    @GetMapping("/product/{productId}/comments")
    public List<CommentResponseDto> getAllComments(@PathVariable Long productId) {
        return commentService.getAllComments(productId);
    }

    // 댓글 작성하기
    @PostMapping("/comment/{productId}")
    public CommentResponseDto createComment(@PathVariable Long productId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestParam String title,
                                            @RequestParam String content,
                                            @RequestParam(required = false) MultipartFile img) throws IOException {
        return commentService.createComment(productId, userDetails, title, content, img);
    }


    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

    }
}
