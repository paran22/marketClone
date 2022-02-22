package com.example.marketclone.controller;

import com.example.marketclone.responseDto.CommentResponseDto;
import com.example.marketclone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    // 댓글 조회하기
    @GetMapping("/product/{productId}/comments")
    public List<CommentResponseDto> getAllComments(@PathVariable Long productId) {
        return commentService.getAllComments(productId);
    }


}
