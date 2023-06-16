package org.example.service;

import org.example.Repository.JwtRepository;
import org.example.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StoreCredentialsService{

    @Value("${application.store.details.url}")
    private String storeDetailsEndPoint;


    public String registerUserInVault(UserDetails data){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Request-Method","POST");
        headers.set("Content-Type","application/json");
        headers.set("Accept","*/*");

        HttpEntity<String> entity = new HttpEntity("{\"userName\":\""+data.getUserName()+"\",\"password\":\""+data.getPassword()+"\"}",headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(storeDetailsEndPoint, entity, String.class);

        return responseEntity.getBody();

    }
}
