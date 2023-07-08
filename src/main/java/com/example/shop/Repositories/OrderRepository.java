package com.example.shop.Repositories;

import com.example.shop.models.Order;
import com.example.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
