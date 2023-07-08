package com.example.shop.controllers;

import com.example.shop.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // Форма создания отзыва пользователем
    @PostMapping(path = "/products/{id}/edit/review/add")
    public String addReview(
            @PathVariable(name = "id") Long productId,
            @RequestParam(name = "productReview") String text,
            @RequestParam(name = "productMark") short mark) {
        reviewService.createReview(productId, text, mark);
        return "redirect:/products/" + productId;
    }

    @PostMapping(path = "/products/{productId}/edit/review/{reviewId}/confirm")
    public String confirmReview(
            @PathVariable(name = "productId") Long productId,
            @PathVariable(name = "reviewId") Long reviewId,
            @RequestParam(name = "released") Boolean released) {
        reviewService.confirmReview(reviewId,released);
        return "redirect:/products/" + productId;
    }
}
