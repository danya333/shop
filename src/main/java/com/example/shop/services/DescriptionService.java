package com.example.shop.services;

import com.example.shop.Repositories.DescriptionRepository;
import com.example.shop.models.Description;
import com.example.shop.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;

    // Сохранить Description
    public void saveDescription(Description description){
        descriptionRepository.save(description);
    }
    // Редактировать характеристики товара
    public void editDescriptions(Product product, List<String> values){
        int i = 0;
        List<Description> descriptions = product.getDescriptions();
        for (Description description : descriptions){
            description.setName(values.get(i));
            saveDescription(description);
            i++;
        }
    }
}
