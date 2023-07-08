package com.example.shop.services;

import com.example.shop.Repositories.CategoryRepository;
import com.example.shop.models.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // Найти все категории товаров
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    // Найти категорию по ID
    public Category findCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
}
