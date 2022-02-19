package com.example.marketclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<ProductInCart> productInCartList = new ArrayList<>();

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long deliveryFee;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    //연관관계 편의 메소드
    public void setUser(User user) {
        this.user = user;
        user.getOrderList().add(this);
    }

    public void setOrder(Long totalPrice, Long deliveryFee) {
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
    }

    //생성 메소드
    public static Order addOrder(Long totalPrice, Long deliveryFee, User user) {
        Order order = new Order();
        order.setOrder(totalPrice, deliveryFee);
        order.setUser(user);
        return order;
    }

}