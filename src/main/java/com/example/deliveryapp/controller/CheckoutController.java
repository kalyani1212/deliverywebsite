package com.example.deliveryapp.controller;

import com.example.deliveryapp.model.DeliveryPartner;
import com.example.deliveryapp.model.Order;
import com.example.deliveryapp.services.DeliveryPartnerService;
import com.example.deliveryapp.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CheckoutController {

    private final OrderService orderService;
    private final DeliveryPartnerService partnerService;

    public CheckoutController(OrderService orderService, DeliveryPartnerService partnerService) {
        this.orderService = orderService;
        this.partnerService = partnerService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Map<String, Object> body) {
        String customerName = (String) body.get("customerName");
        String customerPhone = (String) body.get("customerPhone");
        Double customerLat = Double.parseDouble(body.get("customerLat").toString());
        Double customerLng = Double.parseDouble(body.get("customerLng").toString());

        Order order = orderService.createOrder(customerName, customerPhone, customerLat, customerLng);

        Map<String, Object> resp = new HashMap<>();
        resp.put("orderId", order.getId());
        resp.put("total", order.getTotalPrice());
        resp.put("deliveryCharge", order.getDeliveryCharge());

        if (order.getAssignedPartner() != null) {
            DeliveryPartner p = order.getAssignedPartner();
            long eta = 0;
            if (p.getLatitude() != null && p.getLongitude() != null
                    && order.getCustomerLat() != null && order.getCustomerLng() != null) {
                eta = orderService.computeEtaMinutes(p.getLatitude(), p.getLongitude(),
                        order.getCustomerLat(), order.getCustomerLng());
            }
            Map<String, Object> pd = new HashMap<>();
            pd.put("id", p.getId());
            pd.put("name", p.getName());
            pd.put("phone", p.getPhone());
            pd.put("currentLat", p.getLatitude());
            pd.put("currentLng", p.getLongitude());
            pd.put("etaMinutes", eta);
            resp.put("partner", pd);
        }

        return ResponseEntity.ok(resp);
    }
}
