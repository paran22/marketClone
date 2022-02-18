package com.example.marketclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ProductInOrder {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "productInOrderId")
    private Long productInOrderId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false)
    private Long count;

    @Column(nullable = false)
    private Long sumPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    //연관관계 편의 메소드
    public void setOrder(Order order) {
        this.order = order;
        order.getProductInOrdersList().add(this);
    }

    //생성 메소드
    public ProductInOrder(Product product, Long count, Long sumPrice, Order order) {
        ProductInOrder productInOrder = new ProductInOrder();
        this.product = product;
        this.count = count;
        this.sumPrice = sumPrice;
        productInOrder.setOrder(order);
    }
}
