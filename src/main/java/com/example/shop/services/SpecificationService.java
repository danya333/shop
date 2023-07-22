package com.example.shop.services;

import com.example.shop.Repositories.SpecificationRepository;
import com.example.shop.models.Category;
import com.example.shop.models.Product;
import com.example.shop.models.Specification;
import com.example.shop.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpecificationService {
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;

     // Получить все specifications по категории
    public List<Specification> findSpecificationsByCategoryId(Long categoryId){
        return categoryService.findCategoryById(categoryId).getSpecifications();
    }

    // Получить текущего пользователя
    public User getCurrentUser(){
        return userService.getCurrentUser();
    }

    // Получить product по ID
    public Product findProductById(Long productId){
        return productService.findProductById(productId);
    }
}
