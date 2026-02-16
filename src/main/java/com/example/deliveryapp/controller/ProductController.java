package com.example.deliveryapp.controller;

import com.example.deliveryapp.model.Product;
import com.example.deliveryapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("categories", List.of("Food", "Groceries", "Electronics", "Milk", "Bread", "Restaurant"));
        return "products"; // Renders templates/products.html
    }

    @GetMapping
    @ResponseBody
    public List<Product> listAll() {
        return productRepo.findAll();
    }
}
