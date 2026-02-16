package com.example.deliveryapp.controller;

import com.example.deliveryapp.model.DeliveryPartner;
import com.example.deliveryapp.repository.DeliveryPartnerRepository;
import com.example.deliveryapp.services.DeliveryPartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {
    private final DeliveryPartnerService partnerService;
    private final DeliveryPartnerRepository repo;

    public PartnerController(DeliveryPartnerService partnerService, DeliveryPartnerRepository repo) {
        this.partnerService = partnerService;
        this.repo = repo;
    }

    // partner updates its location
    @PostMapping("/{partnerId}/location")
    public ResponseEntity<?> updateLocation(@PathVariable Long partnerId, @RequestBody Map<String, Object> body) {
        double lat = Double.parseDouble(body.get("lat").toString());
        double lng = Double.parseDouble(body.get("lng").toString());

        DeliveryPartner p = partnerService.updateLocation(partnerId, lat, lng);
        // mark partner available (or use business logic)
        p.setAvailable(true);
        repo.save(p);

        Map<String, Object> resp = new HashMap<>();
        resp.put("id", p.getId());
        resp.put("currentLat", p.getCurrentLat());
        resp.put("currentLng", p.getCurrentLng());
        resp.put("available", p.getAvailable());
        return ResponseEntity.ok(resp);
    }

    // get partner current location
    @GetMapping("/{partnerId}/location")
    public ResponseEntity<?> getLocation(@PathVariable Long partnerId) {
        DeliveryPartner p = repo.findById(partnerId).orElseThrow(() -> new RuntimeException("Partner not found"));
        Map<String, Object> resp = new HashMap<>();
        resp.put("id", p.getId());
        resp.put("currentLat", p.getCurrentLat());
        resp.put("currentLng", p.getCurrentLng());
        resp.put("available", p.getAvailable());
        return ResponseEntity.ok(resp);
    }
}
