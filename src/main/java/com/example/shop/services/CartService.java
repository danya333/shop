package com.example.shop.services;

import com.example.shop.Repositories.CartRepository;
import com.example.shop.Repositories.OrderRepository;
import com.example.shop.Repositories.ProductRepository;
import com.example.shop.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;

    public User getUser(){
        return userService.getCurrentUser();
    }

    // Получить список Cart по Order
    public List<Cart> findCartsByOrder(Order order) {
        return cartRepository.getCartsByOrder(order);
    }

    // Получить стоимость товаров в корзине
    public int getTotalPrice(List<Cart> carts) {
        int total = 0;
        for (Cart cart : carts) {
            total += cart.getPrice();
        }
        return total;
    }

    // Получить корзину по пользователю и статусу ADDED
    public List<Cart> findCartsByUserAndAddedStatus() {
        User user = userService.getCurrentUser();
        return cartRepository.getCartsByUserAndStatus(user, CartProductStatus.ADDED);
    }

    // Добавить товар в корзину
    public void addToCart(Long productId) {
        User user = userService.getCurrentUser();
        Product product = productRepository.findById(productId).orElse(null);
        Cart cart = cartRepository.getCartByUserAndProductAndStatus(user, product, CartProductStatus.ADDED)
                .orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setStatus(CartProductStatus.ADDED);
            cart.setNumber(1);
        } else {
            cart.setNumber(cart.getNumber() + 1);
        }
        cart.setPrice(cart.getNumber() * cart.getProduct().getPrice());
        cartRepository.save(cart);
    }

    // Увеличить в корзине товар на единицу
    public void addOneProduct(Long productId) {
        User user = userService.getCurrentUser();
        Product product = productRepository.findById(productId).orElse(null);
        Cart cart = cartRepository.getCartByUserAndProductAndStatus(user, product, CartProductStatus.ADDED)
                .orElse(null);
        cart.setNumber(cart.getNumber() + 1);
        cart.setPrice(cart.getNumber() * cart.getProduct().getPrice());
        cartRepository.save(cart);
    }

    // Увеличить в корзине товар на единицу
    public void removeOneProduct(Long productId){
        User user = userService.getCurrentUser();
        Product product = productRepository.findById(productId).orElse(null);
        Cart cart = cartRepository.getCartByUserAndProductAndStatus(user, product, CartProductStatus.ADDED)
                .orElse(null);
        if (cart.getNumber() > 1){
            cart.setNumber(cart.getNumber() - 1);
        }
        cart.setPrice(cart.getNumber() * cart.getProduct().getPrice());
        cartRepository.save(cart);
    }

    // Удалить товар из корзины
    public void deleteProduct(Long productId){
        User user = userService.getCurrentUser();
        Product product = productRepository.findById(productId).orElse(null);
        Cart cart = cartRepository.getCartByUserAndProductAndStatus(user, product, CartProductStatus.ADDED)
                .orElse(null);
        if (cart != null){
            cartRepository.delete(cart);
        }
    }

    // Оформить заказ
    public void cartProcess(String address){
        User user = userService.getCurrentUser();
        List<Cart> carts = cartRepository.getCartsByUserAndStatus(user, CartProductStatus.ADDED);
        Order order = new Order();
        order.setAddress(address);
        order.setOrderDate(LocalDate.now());
        order.setUser(user);
        order.setStatus(OrderStatus.PROCESSED);
        orderRepository.save(order);
        for(Cart cart : carts){
            cart.setOrder(order);
            cart.setStatus(CartProductStatus.PROCESSED);
            cartRepository.save(cart);
        }
    }

}
