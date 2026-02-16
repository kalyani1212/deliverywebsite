package com.example.deliveryapp.controller;

import com.example.deliveryapp.model.Product;
import com.example.deliveryapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductWebController {

    @Autowired
    private ProductRepository productRepository;

    // List all products (for Thymeleaf frontend)
    @GetMapping
    public String listProducts(org.springframework.ui.Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products"; // points to src/main/resources/templates/products.html
    }

    // Show add product form (optional, if you want a separate page)
    @GetMapping("/add")
    public String showAddForm() {
        return "addProduct"; // you can create addProduct.html
    }

    // Add a product with image upload
    @PostMapping("/add")
    public String addProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile imageFile) {

        String filename = StringUtils.cleanPath(imageFile.getOriginalFilename());
        Path uploadPath = Paths.get("src/main/resources/static/images");

        try {
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(filename);
            imageFile.transferTo(filePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setImage("/images/" + filename);

        productRepository.save(product);

        return "redirect:/products";
    }

}
