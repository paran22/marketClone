package com.example.marketclone.repository;

import com.example.marketclone.model.Order;
import com.example.marketclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
