package org.example.controller;

import org.apache.camel.ProducerTemplate;
import org.example.model.Order;
import org.example.service.GetTokenService;
import org.example.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Orders")
public class OrderController {

    @Autowired
    private ValidateTokenService validateTokenService;

    @Autowired
    private GetTokenService getTokenService;


    @Autowired
    private ProducerTemplate producerTemplate;


    @PutMapping("/UpdateOrderDetails/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable("orderId") int orderId, @RequestBody Order orderDetails) {

        ResponseEntity.ok(getTokenService.getToken());
        ResponseEntity.ok(validateTokenService.validateToken());

        orderDetails.setOrderId(orderId);

        producerTemplate.sendBody("direct:UpdateOrderDetails", orderDetails);

        return new ResponseEntity<>("Order: " + orderId + "\nUpdated Successfully", HttpStatus.OK);

    }

}
