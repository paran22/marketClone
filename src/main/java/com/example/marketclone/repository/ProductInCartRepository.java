package com.example.marketclone.repository;

import com.example.marketclone.model.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {
//    void deleteAllByProductInCartId(Long productInCartId);
}
