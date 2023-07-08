package com.example.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

    // Главная страница
    @GetMapping(path = "/")
    public String products(Model model) {
        return "main";
    }
}
