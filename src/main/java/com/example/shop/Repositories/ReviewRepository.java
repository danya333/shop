package com.example.shop.Repositories;

import com.example.shop.models.Product;
import com.example.shop.models.Review;
import com.example.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProduct(Product product);
    Optional<Review> getByProduct(Product product);

    Optional<Review> getReviewByProductAndUser(Product product, User user);

}
