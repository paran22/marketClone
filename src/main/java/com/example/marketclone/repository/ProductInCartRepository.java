package com.example.marketclone.repository;

import com.example.marketclone.model.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {
    List<ProductInCart> findAllById(Long cartId);
//    void deleteAllByProductInCartId(Long productInCartId);
}
