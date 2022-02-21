package com.example.marketclone.repository;

import com.example.marketclone.model.Cart;
import com.example.marketclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteAllById(Long productInCartId);
//    void deleteAllByProductInCartId(Long productInCartId);
}
