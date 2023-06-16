package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Repository.JwtRepository;
import org.example.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GenerateTokenService {

    @Value("${application.generateToken.details.url}")
    private String generateTokenUrl;

    @Value("${application.myapp.username}")
    private String generateTokenUserName;

    @Value("${application.myapp.password}")
    private String generateTokenPassword;

    @Autowired
    private JwtRepository jwtRepository;

    public String userToken(String username) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        String str= "{\"userName\":\""+generateTokenUserName+"\",\"password\":\""+generateTokenPassword+"\"}";
        ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(str);

            String userName = jsonNode.get("userName").asText();
            String password = jsonNode.get("password").asText();

            System.out.println(jsonNode);

        ResponseEntity<String> response = restTemplate.postForEntity(generateTokenUrl,jsonNode, String.class);

        Date tokenExpiryTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        dateFormat.format(tokenExpiryTime);

        jwtRepository.setTokenExpiryTime(tokenExpiryTime);

        System.out.println("Token Expiry Time :" + jwtRepository.getTokenExpiryTime());

        int size = response.getBody().length();
        String JWTToken = response.getBody().substring(0,size);
        jwtRepository.setJWTToken(JWTToken);
        System.out.println("JWTToken :" + jwtRepository.getJWTToken());
        return response.getBody();
    }
}
