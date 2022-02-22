package com.example.marketclone.service;

import com.example.marketclone.model.User;
import com.example.marketclone.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;


    public void createComment(User user, String title, String content, MultipartFile img) {
    }
}
