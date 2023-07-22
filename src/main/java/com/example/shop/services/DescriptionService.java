package com.example.shop.services;

import com.example.shop.Repositories.DescriptionRepository;
import com.example.shop.models.Description;
import com.example.shop.models.Product;
import com.example.shop.models.Specification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;
    private final SpecificationService specificationService;
    private final ProductService productService;
    private final CategoryService categoryService;

    // Сохранить Description
    public void saveDescription(Description description) {
        descriptionRepository.save(description);
    }

    // Редактировать характеристики товара
    public void editDescriptions(List<String> values, List<Long> options, Long productId) {
        Product product = productService.findProductById(productId);
        for (int i = 0; i < options.size(); i++) {
            Specification specification = specificationService.findSpecificationById(options.get(i));
            Description description = findDescriptionByProductAndSpecification(product, specification);
            if (description == null){
                Description description1 = new Description();
                description1.setProduct(product);
                description1.setSpecification(specification);
                description1.setName(values.get(i));
                saveDescription(description1);
            } else {
                description.setName(values.get(i));
                saveDescription(description);
            }
        }
    }


    // Получить description по specificationID
    public Description findDescriptionByProductAndSpecification(Product product, Specification specification) {
        return descriptionRepository.findByProductAndSpecification(product, specification).orElse(null);
    }


    // Добавить характеристики товара
    public void addDetails(Long productId, Long categoryId, List<String> values) {
        Product product = productService.findProductById(productId);
        List<Specification> specifications = categoryService.findCategoryById(categoryId).getSpecifications();
        for (int i = 0; i < values.size(); i++) {
            Description description = new Description();
            description.setProduct(product);
            description.setSpecification(specifications.get(i));
            description.setName(values.get(i));
            saveDescription(description);
        }
    }


}
