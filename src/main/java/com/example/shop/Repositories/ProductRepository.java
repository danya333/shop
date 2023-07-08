package com.example.shop.Repositories;

import com.example.shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Long> {

    List<Product> findAllByCategoryName(String categoryName);
    List<Product> findAllByCategoryNameOrderByPriceDesc(String categoryName);
    List<Product> findAllByCategoryNameOrderByPriceAsc(String categoryName);

}
