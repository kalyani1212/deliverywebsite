package com.example.deliveryapp;

import com.example.deliveryapp.model.DeliveryPartner;
import com.example.deliveryapp.model.Product;
import com.example.deliveryapp.repository.DeliveryPartnerRepository;
import com.example.deliveryapp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private DeliveryPartnerRepository dpRepo;

    @Override
    public void run(String... args) throws Exception {
        if (productRepo.count() == 0) {
            Product p1 = new Product();
            p1.setName("Paneer Butter Masala");
            p1.setImage("https://via.placeholder.com/300");
            p1.setPrice(180.0);
            p1.setStock(50);

            Product p2 = new Product();
            p2.setName("Veg Biryani");
            p2.setImage("https://via.placeholder.com/300");
            p2.setPrice(220.0);
            p2.setStock(40);

            productRepo.saveAll(List.of(p1, p2));
        }

        if (dpRepo.count() == 0) {
            DeliveryPartner r = new DeliveryPartner();
            r.setName("Ramesh");
            r.setPhone("9998887771");
            r.setCurrentLat(17.3850);
            r.setCurrentLng(78.4867);
            r.setAvailable(true);
            dpRepo.save(r);
        }
    }
}
