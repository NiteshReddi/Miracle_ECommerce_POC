package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.DaemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GetVaultCredentialsService {


    @Value("${spring.vault.cloud.uri}")
    private String vaultUrl;

    @Value("${spring.vault.cloud.token}")
    private String vaultToken;

    @Autowired
    private DaemonRepository daemonRepository;

    public ResponseEntity<String> getVaultCredentials() throws IOException {

        URL obj = new URL(vaultUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("X-Vault-Token", vaultToken);
        con.setDoOutput(true);

        //String str= con.getResponseMessage();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        String str = sb.toString();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(str);
        String userName = rootNode.get("data").get("data").get("userName").asText();
        String password = rootNode.get("data").get("data").get("password").asText();

       // System.out.println("********** VAULT-USERNAME: " + userName);
      //  System.out.println("********** VAULT-PASSWORD: " + password);


        daemonRepository.setUserName(userName);
        daemonRepository.setPassword(password);

        System.out.println("****************\nuserName :"+ daemonRepository.getUserName()+ "\nPassword :"+daemonRepository.getPassword());

        if (con != null) {
            con.disconnect();
        }
        return new ResponseEntity<String>("Retrieved from Vault Successfully \n\n" +
                "USERNAME :" + userName + "\nPASSWORD :" + password
                , HttpStatus.OK);
    }
}
