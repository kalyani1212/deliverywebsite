package com.example.deliveryapp.controller;

import com.example.deliveryapp.services.OtpService;
import com.example.deliveryapp.model.User;
import com.example.deliveryapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@controller
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private com.example.deliveryapp.services.OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        if (phone == null || phone.isBlank()) return ResponseEntity.badRequest().body(Map.of("error", "phone required"));
        otpService.sendOtp(phone);
        return ResponseEntity.ok(Map.of("message", "OTP sent"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        String otp = body.get("otp");
        if (otpService.verifyOtp(phone, otp)) {
            // find or create user record
            User u = userRepository.findByPhone(phone);
            if (u == null) {
                u = new User();
                u.setPhone(phone);
                u.setName("User-" + phone.substring(Math.max(0, phone.length()-4)));
                userRepository.save(u);
            }
            // Return a simple fake token (replace with real JWT in production)
            String token = "FAKE-TOKEN-" + phone;
            return ResponseEntity.ok(Map.of("message", "verified", "token", token, "userId", u.getId()));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid OTP"));
    }
   
    @GetMapping("/login")
    public String login() {
        return "login"; // This returns templates/login.html
    }
}

