package org.example.service;


import org.example.model.OrderDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CreateOrderService {

    @Value("${application.createOrder.details.url}")
    private String storeOrderDetailsEndpoint;



    public String createNewOrder(OrderDetails orderData){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Request-Method","POST");
        headers.set("Content-Type","application/json");
        headers.set("Accept","*/*");

        HttpEntity<String> entity = new HttpEntity("{\"orderId\":\""+orderData.getOrderId()+"\",\"productType\":\""+orderData.getProductType()+"\",\"address\":\""+orderData.getAddress()+"\",\"price\":\""+orderData.getPrice()+"\"}",headers);

        ResponseEntity<String> response = restTemplate.postForEntity(storeOrderDetailsEndpoint, entity, String.class);

        return response.getBody();

    }

}
