package com.example.shop.controllers;



import com.example.shop.Repositories.ProductRepository;
import com.example.shop.models.Product;
import com.example.shop.pojo.Human;
import com.example.shop.services.TaskService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/task_controller")

public class TaskController {

    private final ProductRepository productRepository;
    private final TaskService taskService;

    @GetMapping(path = "/first_resource")
    public List<String> firstResource(
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to
    ) {
        List<Human> humans = taskService.getHumanByAge(from, to);
        List<String> names = new ArrayList<>();
        for (Human human : humans){
            names.add(human.getName());
        }
        return names;
    }

    @GetMapping(path = "/second_resource")
    public String secondResource(Model model,
                                 @RequestParam(name = "page", required = false) Integer page) {
        if(page != null){
            Pageable pageable = PageRequest.of(page, 2);
            Page<Product> productPage = productRepository.findAll(pageable);
            List<Product> products = productPage.getContent();
            model.addAttribute("products", products);
        } else {
            List<Product> products = productRepository.findAll();
            model.addAttribute("products", products);
        }
        return "data/second_resource_page";
    }

    @GetMapping(path = "/product_resource")
    public String productResource(@RequestParam(name = "categoryName", required = false) String categoryName,
                                  @RequestParam(name = "sort", required = false) String sortType,
                                  Model model) {
        List<Product> products = new ArrayList<>();

        if (categoryName == null){
            products = productRepository.findAll();
        } else if (sortType == null) {
            products = productRepository.findAllByCategoryName(categoryName);
        } else if (sortType.matches("^asc$")){
            products = productRepository.findAllByCategoryNameOrderByPriceAsc(categoryName);
        } else if (sortType.matches("^desc$")){
            products = productRepository.findAllByCategoryNameOrderByPriceDesc(categoryName);
        }

        model.addAttribute("products", products);
        model.addAttribute("categoryName", categoryName);
        return "data/second_resource_page";
    }

}
