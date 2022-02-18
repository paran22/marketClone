package com.example.marketclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "orderId")
    private Long orderId;

    @OneToMany(mappedBy = "order")
    private List<ProductInOrder> productInOrdersList = new ArrayList<>();

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long deliveryFee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    //연관관계 편의 메소드
    public void setUser(User user) {
        this.user = user;
        user.getOrderList().add(this);
    }

    //생성메소드
    public Order(Long totalPrice, Long deliveryFee, User user) {
        Order order = new Order();
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        order.setUser(user);
    }

}