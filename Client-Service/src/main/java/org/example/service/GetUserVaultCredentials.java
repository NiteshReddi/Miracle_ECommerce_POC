package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.Repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class GetUserVaultCredentials {

    @Value("${application.getVaultDetails.details.url}")
    private String getVaultDetailsUserEndPoint;

    public String getUserDetailsFromVault(){

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity =restTemplate.getForEntity(getVaultDetailsUserEndPoint, String.class);

        return responseEntity.getBody();
    }

}
