package com.example.shop.Repositories;


import com.example.shop.models.Description;
import com.example.shop.models.Product;
import com.example.shop.models.Specification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

    @Transactional
    void deleteAllByProduct(Product product);

    Optional<Description> findByProductAndSpecification(Product product, Specification specification);
}
