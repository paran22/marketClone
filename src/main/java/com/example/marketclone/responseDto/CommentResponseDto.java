package com.example.marketclone.responseDto;


import com.example.marketclone.model.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String title;
    private String username;
    private String name;
    private String content;
    private String createdAt;
    private String img;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.title = comment.getTitle();
        this.username = comment.getUser().getUsername();
        this.name = comment.getUser().getName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.img = comment.getImg();
    }


    public CommentResponseDto(Long commentId, String title, String name, String username, String content, String createdAt
            , String img
    ) {
        this.commentId = commentId;
        this.title = title;
        this.username = username;
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
        this.img = img;
    }
}
