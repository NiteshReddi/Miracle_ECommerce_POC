package org.example.service;

import org.example.Repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetOrderDetailsService {


    @Value("${application.getOrderDetails.details.url}")
    private String getOrderEndPoint;

    @Value("${application.getOrderById.details.url}")
    private String getOrderByIdEndPoint;


    public String getOrderDetails() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getOrderEndPoint, String.class);
        return responseEntity.getBody();
    }

    public String getOrderById(int orderId) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getOrderByIdEndPoint+orderId, String.class);
        return responseEntity.getBody();
    }

}
