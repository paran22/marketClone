package com.example.marketclone.service;


import com.example.marketclone.model.Comment;
import com.example.marketclone.model.Product;
import com.example.marketclone.model.User;
import com.example.marketclone.repository.CommentRepository;
import com.example.marketclone.repository.ProductRepository;
import com.example.marketclone.responseDto.CommentResponseDto;
import com.example.marketclone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final S3Uploader s3Uploader;


    // 댓글 조회하기
    public List<CommentResponseDto> getAllComments(Long productId) {
        // 반환할 리스트
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        
//        // productId로 댓글을 찾아서
//        Comment comment = commentRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
//        // 댓글 전체를 불러와 리스트에 저장한다
//        List<Comment> commentList = comment.getProduct().getCommentList();
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품이 없습니다."));
        List<Comment> commentList = product.getCommentList();

        // for문을 돌면서 정보들 찾아 1. commentId , 2. title , 3. username, 4. name, 5. content, 6.createdAt, 7.img
        for (Comment eachComment : commentList) {
            Long commentId = eachComment.getId();
            String title = eachComment.getTitle();
            String username = eachComment.getUser().getUsername();
            String name = eachComment.getUser().getName();
            String content = eachComment.getContent();
            String createdAt = eachComment.getCreatedAt();
            String img = eachComment.getImg();
            // responseDto에 보낼 정보들 넣어줘
            CommentResponseDto commentResponseDto = new CommentResponseDto(commentId, title, name, username, content, createdAt
                    , img
            );
            //반환할 리스트에 하나씩 넣어준다
            commentResponseDtoList.add(commentResponseDto);
        }
        // 리스트를 돌려준다
        return commentResponseDtoList;
    }

    public CommentResponseDto createComment(Long productId, UserDetailsImpl userDetails, String title, String content, MultipartFile img) throws IOException {

        // 상품, 유저 정보
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));
        User user = userDetails.getUser();

        // 이미지 저장하고 url과 fileName 받기
        String imgUrl = "";
        String fileName = "";
        if(img != null) {
            HashMap<String,String> imgInfo = s3Uploader.upload(img,"review");
            imgUrl = imgInfo.get("img");
            fileName = imgInfo.get("fileName");
        }

        // 댓글 생성 및 저장
        Comment comment = Comment.addComment(product, title, user, content, imgUrl, fileName);
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getTitle(), user.getName(),
                user.getUsername(), comment.getContent(), comment.getCreatedAt(), comment.getImg());
    }


    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 저장된 이미지가 있으면 S3에서 저장된 이미지 지우기
        if (!comment.getImg().equals("")) {
            s3Uploader.deleteFile(comment.getFilename());
        }

        // comment와 product의 관계를 끊기
        comment.removeProduct();

        commentRepository.delete(comment);
    }

}
