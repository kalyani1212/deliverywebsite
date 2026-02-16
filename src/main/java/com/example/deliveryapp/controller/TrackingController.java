package com.example.deliveryapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tracking")
@CrossOrigin(origins = "http://localhost:3000")
public class TrackingController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * Delivery partner (or simulation) POSTs: {"orderId":123, "lat":17.38, "lng":78.48}
     * Backend will broadcast to /topic/tracking.{orderId}
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateLocation(@RequestBody Map<String, Object> body) {
        Object oOrder = body.get("orderId");
        Object oLat = body.get("lat");
        Object oLng = body.get("lng");

        if (oOrder == null || oLat == null || oLng == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "orderId, lat and lng required"));
        }

        String orderTopic = "/topic/tracking." + String.valueOf(oOrder);
        Map<String, Object> msg = Map.of(
                "orderId", oOrder,
                "lat", Double.parseDouble(String.valueOf(oLat)),
                "lng", Double.parseDouble(String.valueOf(oLng))
        );

        template.convertAndSend(orderTopic, msg);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
