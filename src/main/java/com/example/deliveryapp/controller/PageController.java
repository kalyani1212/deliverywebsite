package com.example.deliveryapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "login"; // shows login.html
    }

    @GetMapping("/verify")
    public String verify() {
        return "verify-otp"; // shows verify-otp.html
    }

    @GetMapping("/index")
    public String index() {
        return "index"; // shows index.html
    }

//    @GetMapping("/products")
//    public String products() {
//        return "products"; // shows products.html
//    }

    @GetMapping("/cart")
    public String cart() {
        return "cart"; // shows cart.html
    }
}
