package com.example.shop.controllers;

import com.example.shop.models.*;
import com.example.shop.services.CartService;
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
public class CartController {
    private final CartService cartService;

    // Корзина
    @GetMapping(path = "/cart")
    public String cart(Model model) {
        List<Cart> carts = cartService.findCartsByUserAndAddedStatus();
        int total = cartService.getTotalPrice(carts);
        model.addAttribute("carts", carts);
        model.addAttribute("total", total);
        return "cart";
    }

    // Добавление товара в корзину
    @GetMapping(path = "/products/{productId}/addToCart")
    public String addToCart(@PathVariable(name = "productId") Long productId) {
        cartService.addToCart(productId);
        return "redirect:/products/" + productId;
    }

    // Увеличение в корзине товара на единицу
    @GetMapping(path = "/cart/{productId}/add")
    public String addProduct(@PathVariable(name = "productId") Long productId) {
        cartService.addOneProduct(productId);
        return "redirect:/cart";
    }

    // Уменьшение в корзине товара на единицу
    @GetMapping(path = "/cart/{productId}/remove")
    public String removeProduct(@PathVariable(name = "productId") Long productId) {
        cartService.removeOneProduct(productId);
        return "redirect:/cart";
    }

    // Удаление товара из корзины
    @GetMapping(path = "/cart/{productId}/delete")
    public String deleteProduct(@PathVariable(name = "productId") Long productId) {
        cartService.deleteProduct(productId);
        return "redirect:/cart";
    }

    // Оформление заказа в корзине
    @PostMapping(path = "/cart/process")
    public String cartProcess(@RequestParam(name = "address") String address) {
        cartService.cartProcess(address);
        return "redirect:/products";
    }
}
