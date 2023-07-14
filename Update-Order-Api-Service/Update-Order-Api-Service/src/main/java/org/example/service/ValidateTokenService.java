package org.example.service;

import org.example.repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidateTokenService {


    @Value("${application.validateToken.url}")
    private String validateTokenUrl;

    @Autowired
    private JwtRepository jwtRepository;


    public String validateToken(){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Request-Method","POST");
        headers.set("Content-Type","application/json");
        headers.set("Accept","*/*");
        System.out.println(jwtRepository.getJWTToken());
        headers.set("Authorization", "Bearer "+ jwtRepository.getJWTToken());

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity =restTemplate.exchange(validateTokenUrl, HttpMethod.GET,entity,String.class);

        return responseEntity.getBody();

    }

}
