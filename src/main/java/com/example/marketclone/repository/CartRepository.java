package com.example.marketclone.repository;

import com.example.marketclone.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteAllByProductInCartId(Long productInCartId);
}
