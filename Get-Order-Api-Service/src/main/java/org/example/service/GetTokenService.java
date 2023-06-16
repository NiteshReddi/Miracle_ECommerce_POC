package org.example.service;

import org.example.repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetTokenService {

    @Autowired
    private JwtRepository jwtRepository;

    @Value("${application.getJwtToken.url}")
    private String getTokenUrl;

    public String getToken() {

        RestTemplate restTemplate = new RestTemplate();

        System.out.println(getTokenUrl);

        ResponseEntity<String> response = restTemplate.getForEntity(getTokenUrl, String.class);

        String JWTToken = response.getBody();

        jwtRepository.setJWTToken(JWTToken);
       System.out.println("JWTToken :" + jwtRepository.getJWTToken());
        return response.getBody();
    }
}
