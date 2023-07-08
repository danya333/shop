package com.example.shop.services;

import com.example.shop.Repositories.ReviewRepository;
import com.example.shop.models.Product;
import com.example.shop.models.Review;
import com.example.shop.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final UserService userService;

    // Создать отзыв
    public void createReview(Long productId, String text, short mark){
        Product product = productService.findProductById(productId);
        User user = userService.getCurrentUser();
        Review review = new Review();
        review.setProduct(product);
        review.setMark(mark);
        review.setText(text);
        review.setReleased(false);
        review.setPublicationDate(LocalDate.now());
        review.setUser(user);
        reviewRepository.save(review);
    }

    // Подтверждение отзыва админом
    public void confirmReview(Long reviewId, Boolean released){
        Review review = reviewRepository.findById(reviewId).orElse(null);
        review.setReleased(released);
        reviewRepository.save(review);
    }

    // Получить список отзывов по Product
    public List<Review> getReviews(Product product) {
        List<Review> reviews = reviewRepository.findAllByProduct(product);
        return reviews;
    }

    // Получить список отзывов, одобренных модератором
    public List<Review> getCheckedReviews(Product product) {
        List<Review> reviews = reviewRepository.findAllByProduct(product);
        List<Review> checkedReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.isReleased()) {
                checkedReviews.add(review);
            }
        }
        return checkedReviews;
    }

    // Получить среднюю оценку товара
    public double getAverageMark(Product product) {
        int count = getCheckedReviews(product).size();
        int sum = 0;
        for (Review review : getCheckedReviews(product)) {
            sum += review.getMark();
        }
        if (count != 0) {
            return sum / count;
        }
        return sum;
    }

    // Проверка: оставлял ли пользователь отзыв на товар
    public boolean reviewCheck(Product product, User user) {
        Review userReview = reviewRepository.getReviewByProductAndUser(product, user).orElse(null);
        boolean check = false;
        if (userReview != null) {
            check = true;
        }
        return check;
    }

}
