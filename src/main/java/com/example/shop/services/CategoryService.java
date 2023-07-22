package com.example.shop.services;

import com.example.shop.Repositories.CategoryRepository;
import com.example.shop.models.Category;
import com.example.shop.models.Description;
import com.example.shop.models.Specification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final DescriptionService descriptionService;
    private final ProductService productService;

    // Найти все категории товаров
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    // Найти категорию по ID
    public Category findCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }

    // Добавить характеристики товару
    public void productDetailsForm(Long categoryId, Long productId, List<String> values){
        List<Specification> specifications = findCategoryById(categoryId).getSpecifications();
        int i = 0;
        for (String value : values){
            Description description = new Description();
            description.setProduct(productService.findProductById(productId));
            description.setSpecification(specifications.get(i));
            description.setName(value);
            descriptionService.saveDescription(description);
            i++;
        }
    }

}
