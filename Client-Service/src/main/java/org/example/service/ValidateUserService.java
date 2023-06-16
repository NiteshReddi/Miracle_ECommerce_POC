package org.example.service;

import org.example.Repository.JwtRepository;
import org.example.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidateUserService {

    @Value("${application.validate.details.url}")
    private String validateUserEndPoint;


    public String validateVaultUser(UserDetails data) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Request-Method","POST");
        headers.set("Content-Type","application/json");
        headers.set("Accept","*/*");

        HttpEntity<String> entity = new HttpEntity("{\"userName\":\""+data.getUserName()+"\",\"password\":\""+data.getPassword()+"\"}",headers);

        ResponseEntity<String> response = restTemplate.postForEntity(validateUserEndPoint, entity, String.class);

        return response.getBody();

    }
}
