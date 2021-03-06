package com.example.marketclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductInCart {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // fetch 기본값 EAGER
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false)
    private Long count;

    // fetch 기본값 EAGER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    //order 혹은 cart
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductState state;

    // fetch 기본값 EAGER
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
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

    public void setProductInCart(Product product, Long count) {
        this.product = product;
        this.count = count;
        this.state = ProductState.CART;
    }

    //생성 메소드
    public static ProductInCart addProductInCart(Product product, Long count, Cart cart) {
        ProductInCart productInCart = new ProductInCart();
        productInCart.setProductInCart(product, count);
        productInCart.setCart(cart);
        return productInCart;
    }

    // cart 연결 제거
    public void removeCart() {
        this.cart.getProductInCartList().remove(this);
        this.cart = null;
    }
}
