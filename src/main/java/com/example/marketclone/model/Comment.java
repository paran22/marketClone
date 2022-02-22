package com.example.marketclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String img;

    // fetch 기본값 EAGER
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        product.getCommentList().add(this);
    }


}
