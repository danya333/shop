package com.example.shop.controllers;

import com.example.shop.models.Product;
import com.example.shop.models.Review;
import com.example.shop.models.User;
import com.example.shop.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final DescriptionService descriptionService;
    private final SpecificationService specificationService;


    @GetMapping(path = "/")
    public String main() {
        return "redirect:/products";
    }

    // Страница с товарами
    @GetMapping(path = "/products")
    public String products(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        model.addAttribute("user", userService.getCurrentUser());
        return "products";
    }

    // Страница создания товара
    @GetMapping(path = "/products/create")
    public String createProduct(Model model) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("user", userService.getCurrentUser());
        return "createProduct";
    }

    // Форма создания товара
    @PostMapping(path = "/products/create")
    public String addProduct(
            @RequestParam(name = "productName") String productName,
            @RequestParam(name = "productPrice") int productPrice,
            @RequestParam(name = "categoryId") Long categoryId
    ) {
        Long productId = productService.createProduct(productName, productPrice, categoryId);
        return "redirect:/productDetails/" + categoryId + "/" + productId;
    }

    // Страница редактирования товара
    @GetMapping(path = "/products/{id}/edit")
    public String editProducts(@PathVariable(name = "id") Long productId, Model model) {
        Product product = productService.findProductById(productId);
        model.addAttribute("product", product);
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("productDetails", productService.getProductDetails(product));
        model.addAttribute("descriptions", product.getDescriptions());
        model.addAttribute("specifications", specificationService
                .findSpecificationsByCategoryId(product.getCategory().getId()));
        return "editProduct";
    }

    // Форма редактирования товара
    @PostMapping(path = "/products/{id}/edit")
    public String editProduct(
            @PathVariable(name = "id") Long productId,
            @RequestParam(name = "productName") String productName,
            @RequestParam(name = "productPrice") int productPrice,
            @RequestParam(name = "value") List<String> values,
            @RequestParam(name = "option") List<Long> options
    ) {
        productService.editProduct(productId, productName, productPrice);
        descriptionService.editDescriptions(values, options, productId);
        return "redirect:/products";
    }

    // Страница информации о товаре
    @GetMapping(path = "/products/{id}")
    public String productInfo(@PathVariable(name = "id") Long productId, Model model) {
        Product product = productService.findProductById(productId);
        List<Review> checkedReviews = reviewService.getCheckedReviews(product);
        List<Review> reviews = reviewService.getReviews(product);
        Double mark = reviewService.getAverageMark(product);
        User user = userService.getCurrentUser();

        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("checkedReviews", checkedReviews);
        model.addAttribute("reviews", reviews);
        model.addAttribute("mark", mark);
        model.addAttribute("reviewCheck", reviewService.reviewCheck(product, user));
        model.addAttribute("productDetails", productService.getProductDetails(product));
        return "productInfo";
    }

    // Удаление товара
    @GetMapping(path = "/products/{id}/delete")
    public String deleteProduct(@PathVariable(name = "id") Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }

}
