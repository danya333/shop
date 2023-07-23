package com.example.shop.services;

import com.example.shop.Repositories.CategoryRepository;
import com.example.shop.Repositories.DescriptionRepository;
import com.example.shop.Repositories.ProductRepository;
import com.example.shop.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DescriptionRepository descriptionRepository;

    // Найти все товары
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // Найти товар по ID
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Создать товар
    public Long createProduct(String productName, int productPrice, Long categoryId) {
        Product product = new Product();
        product.setName(productName);
        product.setPrice(productPrice);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        product.setCategory(category);
        productRepository.save(product);
        return product.getId();
    }

    // Редактировать товар
    public void editProduct(Long id, String name, int price) {
        Product product = findProductById(id);
        product.setName(name);
        product.setPrice(price);
        productRepository.save(product);
    }

    // Удалить товар
    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        descriptionRepository.deleteAllByProduct(product);
        productRepository.delete(product);
    }

}
