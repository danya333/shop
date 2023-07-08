package com.example.shop.controllers;

import com.example.shop.models.Product;
import com.example.shop.models.Review;
import com.example.shop.models.User;
import com.example.shop.services.CategoryService;
import com.example.shop.services.ProductService;
import com.example.shop.services.ReviewService;
import com.example.shop.services.UserService;
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
        return "createProduct";
    }

    // Форма создания товара
    @PostMapping(path = "/product/add")
    public String addProduct(
            @RequestParam(name = "productName") String productName,
            @RequestParam(name = "productPrice") int productPrice,
            @RequestParam(name = "categoryId") Long categoryId
    ) {
        productService.createProduct(productName, productPrice, categoryId);
        return "redirect:/products";
    }

    // Страница редактирования товара
    @GetMapping(path = "/products/{id}/edit")
    public String editProducts(@PathVariable(name = "id") Long productId, Model model) {
        model.addAttribute("product", productService.findProductById(productId));
        return "editProduct";
    }

    // Форма редактирования товара
    @PostMapping(path = "/products/{id}/edit")
    public String editProduct(
            @PathVariable(name = "id") Long productId,
            @RequestParam(name = "productName") String productName,
            @RequestParam(name = "productPrice") int productPrice
    ) {
        productService.editProduct(productId, productName, productPrice);
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
        return "productInfo";
    }

    // Удаление товара
    @GetMapping(path = "/products/{id}/delete")
    public String deleteProduct(@PathVariable(name = "id") Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }
}
