package com.example.marketclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    // fetch 기본값 LAZY
    @OneToMany(mappedBy = "user")
    private List<Order> orderList = new ArrayList<>();

    // fetch 기본값 EAGER
    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    public User(String username, String password, String email, String name, Cart cart){
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.cart = cart;
    }

    // cart 업데이트할 때 사용
    public void setCart(Cart cart) {
        this.cart = cart;
    }


}
