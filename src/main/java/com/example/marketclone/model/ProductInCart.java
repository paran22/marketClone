package com.example.marketclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ProductInCart {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false)
    private Long count;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    //이후 type 수정할 수 있습니다
    @Column(nullable = false)
    private String state;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    //연관관계 편의 메소드
    public void setCart(Cart cart) {
        this.cart = cart;
        cart.getProductInCartList().add(this);
    }

    public void setOrder(Order order) {
        this.order = order;
        order.getProductInCartList().add(this);
    }

    //생성 메소드
    public ProductInCart(Product product, Long count, String state, Cart cart) {
        ProductInCart productInCart = new ProductInCart();
        this.product = product;
        this.count = count;
        this.state = state;
        productInCart.setCart(cart);
    }


}
