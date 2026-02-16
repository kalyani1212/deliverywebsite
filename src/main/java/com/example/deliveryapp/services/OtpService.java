package com.example.deliveryapp.services;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

@Service
public class OtpService {
    private ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();
    private Random rnd = new Random();

    public void sendOtp(String phone) {
        String otp = String.format("%04d", rnd.nextInt(10000));
        store.put(phone, otp);
        // In prod: call Twilio/MSG91 to send SMS. For now print to console
        System.out.println("OTP for " + phone + " = " + otp);
    }

    public boolean verifyOtp(String phone, String otp) {
        return otp != null && otp.equals(store.get(phone));
    }
}
