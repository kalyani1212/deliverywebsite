package com.example.deliveryapp.services;

import com.example.deliveryapp.model.DeliveryPartner;
import com.example.deliveryapp.model.Order;
import com.example.deliveryapp.repository.DeliveryPartnerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryPartnerService {

    private final DeliveryPartnerRepository repo;

    public DeliveryPartnerService(DeliveryPartnerRepository repo) {
        this.repo = repo;
    }

    // Assign nearest available partner based on Haversine distance
    public Optional<DeliveryPartner> assignNearestPartner(Order order) {
        Double custLat = order.getCustomerLat();
        Double custLng = order.getCustomerLng();

        List<DeliveryPartner> avail = repo.findByAvailableTrue();

        if (avail.isEmpty()) {
            // fallback: any partner (even not available)
            return repo.findAll().stream().findAny();
        }

        return avail.stream()
                .filter(p -> p.getCurrentLat() != null && p.getCurrentLng() != null)
                .min(Comparator.comparingDouble(p ->
                        haversineDistanceKm(p.getCurrentLat(), p.getCurrentLng(), custLat, custLng)
                ));
    }

    // Update partner location and availability
    public DeliveryPartner updateLocation(Long partnerId, double lat, double lng) {
        DeliveryPartner p = repo.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        p.setCurrentLat(lat);
        p.setCurrentLng(lng);
        // Availability logic can be updated if needed
        repo.save(p);
        return p;
    }

    /** Haversine formula to compute distance in km */
    public static double haversineDistanceKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
