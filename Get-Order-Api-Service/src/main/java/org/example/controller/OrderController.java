package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.service.GetTokenService;
import org.example.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ValidateTokenService validateTokenService;

    @Autowired
    private GetTokenService getTokenService;


    @GetMapping("/getOrderDetailsById/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable int orderId) {

       ResponseEntity.ok(getTokenService.getToken());
       ResponseEntity.ok(validateTokenService.validateToken());

        try {
            Order order = orderRepository.findByOrderId(orderId);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders(){

       ResponseEntity.ok(getTokenService.getToken());
       ResponseEntity.ok(validateTokenService.validateToken());

       return orderRepository.findAll();
    }

}




