package com.example.deliveryapp.services;

import org.springframework.stereotype.Service;
import com.example.deliveryapp.model.Order;
import com.example.deliveryapp.repository.OrderRepository; // your JPA repo
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(String customerName, String address, double latitude, double longitude) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setCustomerLat(latitude);
        order.setCustomerLng(longitude);
        order.setStatus("PLACED");
        return orderRepository.save(order);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public long computeEtaMinutes(Object object, Object object2, Double customerLat, Double customerLng) {
        // Implement ETA calculation logic
        return 0;
    }
}
