package com.example.marketclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Cart {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "cartId")
    private Long cartId;

    @OneToMany(mappedBy = "cart")
    private List<ProductInCart> productInCartList = new ArrayList<>();

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long deliveryFee;

}
