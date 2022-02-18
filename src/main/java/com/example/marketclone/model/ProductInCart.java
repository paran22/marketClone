package com.example.marketclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ProductInCart {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "productInCartId")
    private Long productInCartId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false)
    private Long count;

    @Column(nullable = false)
    private Long sumPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    //연관관계 편의 메소드
    public void setCart(Cart cart) {
        this.cart = cart;
        cart.getProductInCartList().add(this);
    }

    //생성 메소드
    public ProductInCart(Product product, Long count, Long sumPrice, Cart cart) {
        ProductInCart productInCart = new ProductInCart();
        this.product = product;
        this.count = count;
        this.sumPrice = sumPrice;
        productInCart.setCart(cart);
    }


}
