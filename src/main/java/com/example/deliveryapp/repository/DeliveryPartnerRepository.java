package com.example.deliveryapp.repository;

import com.example.deliveryapp.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {
    List<DeliveryPartner> findByAvailableTrue();
}
