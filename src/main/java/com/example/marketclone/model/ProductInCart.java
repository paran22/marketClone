package com.example.marketclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
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

    //order 혹은 cart
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

    public void setProductInCart(Product product, Long count, String state) {
        this.product = product;
        this.count = count;
        this.state = state;
    }

    //생성 메소드
    public static ProductInCart addProductInCart(Product product, Long count,
                                                 String state, Cart cart) {
        ProductInCart productInCart = new ProductInCart();
        productInCart.setProductInCart(product, count, state);
        productInCart.setCart(cart);
        return productInCart;
    }

    // cart 연결 제거
    public void removeCart() {
        this.cart.getProductInCartList().remove(this);
        this.cart = null;
    }
}
