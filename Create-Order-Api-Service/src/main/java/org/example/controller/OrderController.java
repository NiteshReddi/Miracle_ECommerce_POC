package org.example.controller;

import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.service.GetTokenService;
import org.example.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ValidateTokenService validateTokenService;

    @Autowired
    private GetTokenService getTokenService;


    @PostMapping("/createOrderService")
    public String saveOrder(@RequestBody Order orderDetails){

       ResponseEntity.ok(getTokenService.getToken());
       ResponseEntity.ok(validateTokenService.validateToken());

        orderRepository.save(orderDetails);

        System.out.println("##########\n"+orderDetails);

        return "\nOrder Placed with order ID :"+orderDetails.getOrderId();
    }

}
