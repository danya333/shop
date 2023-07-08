package com.example.shop.controllers;

import com.example.shop.models.*;
import com.example.shop.services.CartService;
import com.example.shop.services.OrderService;
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
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    // Страница заказов
    @GetMapping(path = "/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findAllOrders());
        return "orders";
    }

    // Информация о заказе
    @GetMapping(path = "/orders/{id}")
    public String productInfo(@PathVariable(name = "id") Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);
        List<Cart> carts = cartService.findCartsByOrder(order);
        int total = cartService.getTotalPrice(carts);

        model.addAttribute("carts", carts);
        model.addAttribute("total", total);
        model.addAttribute("order", order);
        return "orderInfo";
    }

    // Форма изменения статуса заказа
    @PostMapping(path = "/orders/{orderId}/change")
    public String orderStatus(@RequestParam(name = "orderStatus") String status,
                              @PathVariable(name = "orderId") Long orderId) {
        orderService.editOrderStatus(orderId, status);
        return "redirect:/orders";
    }
}
