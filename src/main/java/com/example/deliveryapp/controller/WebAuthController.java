package com.example.deliveryapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebAuthController {

    // Step 1: Send OTP
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam("phone") String phone,
                          HttpSession session,
                          Model model) {
        int otp = (int)(Math.random() * 9000) + 1000;
        session.setAttribute("otp", otp);
        session.setAttribute("phone", phone);

        System.out.println("Generated OTP for " + phone + " : " + otp);

        model.addAttribute("phone", phone);
        model.addAttribute("message", "OTP sent successfully!");

        return "verify-otp";
    }

    // Step 2: Verify OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("phone") String phone,
                            @RequestParam("otp") int enteredOtp,
                            HttpSession session,
                            Model model) {
        Integer generatedOtp = (Integer) session.getAttribute("otp");
        String sessionPhone = (String) session.getAttribute("phone");

        if (generatedOtp != null && sessionPhone != null
                && sessionPhone.equals(phone)
                && generatedOtp == enteredOtp) {
            model.addAttribute("userPhone", phone);
            return "index";
        } else {
            model.addAttribute("phone", phone);
            model.addAttribute("error", "Invalid OTP. Please try again.");
            return "verify-otp";
        }
    }

    // Serve login page
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }
}
