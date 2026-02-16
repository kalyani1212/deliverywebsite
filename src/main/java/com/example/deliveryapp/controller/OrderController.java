package com.example.deliveryapp.controller;

import com.example.deliveryapp.model.Order;
import com.example.deliveryapp.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get order by ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    // Create order from cart
    @PostMapping("/checkout")
    public Order checkout(@RequestParam String customerName,
                          @RequestParam String address,
                          @RequestParam double latitude,
                          @RequestParam double longitude) {
        return orderService.createOrder(customerName, address, latitude, longitude);
    }

    // Update order (status update, e.g., DELIVERED)
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        return orderService.saveOrder(order);
    }

    // Delete order
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
