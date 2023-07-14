//package org.example.service;
//import org.example.model.Order;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class OrderUpdateListener {
//
//    @JmsListener(destination = "order.update.queue")
//    public void receiveMessage(Order orderId) {
//        System.out.println("Received order update for orderId: " + orderId.getOrderId());
//    }
//}
