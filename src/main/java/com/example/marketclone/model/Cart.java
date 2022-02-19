package com.example.marketclone.model;

import com.example.marketclone.dto.CartRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cart {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @OneToMany(mappedBy = "cart")
    private List<ProductInCart> productInCartList = new ArrayList<>();

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long deliveryFee;

    public Cart(List<ProductInCart> productInCartList, Long totalPrice, Long deliveryFee) {
        this.productInCartList = productInCartList;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
    }


    public Cart(Long totalPrice, Long deliveryFee) {
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
    }

}
