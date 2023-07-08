package com.example.shop.Repositories;


import com.example.shop.models.Description;
import com.example.shop.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

    @Transactional
    void deleteAllByProduct(Product product);
}
