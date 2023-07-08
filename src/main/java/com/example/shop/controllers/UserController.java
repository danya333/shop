package com.example.shop.controllers;

import com.example.shop.models.User;
import com.example.shop.services.OrderService;
import com.example.shop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    // Страница регистрации
    @GetMapping(path = "/registration")
    public String registration(){
        return "/registration";
    }

    // Форма создания пользователя
    @PostMapping(path = "/user/create")
    public String createUser(
            @RequestParam(name = "userName") String userName,
            @RequestParam(name = "userSurname") String userSurname,
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password
    ){
        userService.createUser(userName, userSurname, login, password);
        return "redirect:/products";
    }

    // Личный кабинет
    @GetMapping(path = "/user")
    public String products(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("orders", orderService.findAllOrdersByUser());
        return "user";
    }
}
