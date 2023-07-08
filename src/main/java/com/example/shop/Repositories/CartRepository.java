package com.example.shop.Repositories;

import com.example.shop.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> getCartByUserAndProductAndStatus(User user, Product product, CartProductStatus status);
    List<Cart> getCartsByUserAndStatus(User user, CartProductStatus status);
    List<Cart> getCartsByOrder(Order order);
}
