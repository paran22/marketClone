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


}
