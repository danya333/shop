package com.example.shop.controllers;

import com.example.shop.models.Product;
import com.example.shop.models.Specification;
import com.example.shop.services.CategoryService;
import com.example.shop.services.DescriptionService;
import com.example.shop.services.ProductService;
import com.example.shop.services.SpecificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class CategoryController {
    private final SpecificationService specificationService;
    private final DescriptionService descriptionService;
    private final ProductService productService;

    // Страница добавления характеристик товару /productDetails
    @GetMapping(path = "/productDetails/{categoryId}/{productId}")
    public String productDetails(@PathVariable(name = "categoryId") Long categoryId,
                                 @PathVariable(name = "productId") Long productId,
                                 Model model
    ){
        Product product = productService.findProductById(productId);
        model.addAttribute("specifications", specificationService.findSpecificationsByCategoryId(categoryId));
        model.addAttribute("user", specificationService.getCurrentUser());
        model.addAttribute("product", product);
        model.addAttribute("categoryId", categoryId);
        return "productDetails";
    }

    // Форма добавления характеристик товару /productDetails
    @PostMapping(path = "/productDetails/{categoryId}/{productId}")
    public String productDetailsForm(@PathVariable(name = "productId") Long productId,
                                 @PathVariable(name = "categoryId") Long categoryId,
                                 @RequestParam(name = "value") List<String> values
    ){
        descriptionService.addDetails(productId, categoryId, values);
        return "redirect:/products";
    }
}


