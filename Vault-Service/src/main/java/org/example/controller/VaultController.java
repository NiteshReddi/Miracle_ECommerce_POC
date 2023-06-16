package org.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.entity.AuthRequest;
import org.example.entity.LogResponse;
import org.example.repository.DaemonRepository;
import org.example.service.GetTokenService;
import org.example.service.GetVaultCredentialsService;
import org.example.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;


@RestController
public class VaultController {

    @Value("${spring.vault.cloud.uri}")
    private String vaultUrl;

    @Value("${spring.vault.cloud.token}")
    private String vaultToken;

    @Autowired
    private ValidateTokenService validateTokenService;

    @Autowired
    private GetTokenService getTokenService;

    @Autowired
    private GetVaultCredentialsService getVaultCredentialsService;

    @Autowired
    private DaemonRepository daemonRepository;

    private static final String RESILIENCE4J_INSTANCE_NAME = "vaultService";


    @PostMapping("/storeSecret")
    public ResponseEntity<String> storeSecret(@RequestBody AuthRequest data) throws MalformedInputException, IOException {


        ResponseEntity.ok(getTokenService.getToken());

        ResponseEntity.ok(validateTokenService.validateToken());

        URL obj = new URL(vaultUrl);

        try {
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("X-Vault-Token", vaultToken);
            con.setDoOutput(true);
            String jsonBody = "{\"data\":{\"userName\":\"" + data.getUserName() + "\",\"password\":\"" + data.getPassword() + "\"}}";

            System.out.println(jsonBody);

                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(jsonBody);

                int responseCode = con.getResponseCode();
                System.out.println("Response Code : " + responseCode);

                wr.flush();
                wr.close();
        }catch(Exception e){
            return new ResponseEntity<String>("Failed to Connect to Vault Server\nException:"+e,HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<String>("Stored in Vault Successfully", HttpStatus.OK);
    }


    //Retrieve Credentials from Vault
    @GetMapping("/retrieveVaultCredentials")
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = "fallbackGetCredentials")
    public ResponseEntity<String> getSecret() throws IOException {

       ResponseEntity.ok(getTokenService.getToken());
       ResponseEntity.ok(validateTokenService.validateToken());

        URL obj = new URL(vaultUrl);
      try {
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

          if (con != null)
              con.disconnect();

          return new ResponseEntity<String>("Retrieved from Vault Successfully \n\n" +
                  "USERNAME :" + userName + "\nPASSWORD :" + password
                  , HttpStatus.OK);
      }catch(Exception e){
          return new ResponseEntity<String>("Failed to Connect to Vault Server\nException:"+e,HttpStatus.SERVICE_UNAVAILABLE);
      }
    }

    public ResponseEntity<String>  fallbackGetCredentials(Throwable t) throws MalformedInputException {

        ResponseEntity.ok(getTokenService.getToken());
        ResponseEntity.ok(validateTokenService.validateToken());

      String daemonUserName =  daemonRepository.getUserName();
      String daemonPassword = daemonRepository.getPassword();

      return new ResponseEntity<String>("Retrieved from \"Daemon Repository\" Successfully \n\n" +
              "USERNAME :" + daemonUserName + "\nPASSWORD :" + daemonPassword
              , HttpStatus.OK);
    }



    //Validate User Credentials from Vault
    @PostMapping("/validateUser")
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = "fallBackMethodValidateUser")
    public LogResponse Authenticate(@RequestBody AuthRequest authRequest) throws Exception {

        ResponseEntity.ok(getTokenService.getToken());
        ResponseEntity.ok(validateTokenService.validateToken());

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
            String username = rootNode.get("data").get("data").get("userName").asText();
            String password = rootNode.get("data").get("data").get("password").asText();


            if (authRequest.getUserName().equals(username) && authRequest.getPassword().equals(password)) {
                // return "Welcome to "+username+"!!!\n\n Credentials Matched with Vault";
                LogResponse logResponse = LogResponse.builder().valid(true).response("Welcome to " + username + "\nCredentials Matched with Vault").build();
                return logResponse;
            } else {
                LogResponse logResponse = LogResponse.builder().valid(false).response("Invalid UserName (OR) Password...\n Please Enter Valid Credentials").build();
                return logResponse;
            }
        }

    public  LogResponse fallBackMethodValidateUser(@RequestBody AuthRequest authRequest, Throwable t) throws MalformedInputException {


        ResponseEntity.ok(getTokenService.getToken());

        ResponseEntity.ok(validateTokenService.validateToken());

        String daemonUserName = daemonRepository.getUserName();
        String daemonPassword = daemonRepository.getPassword();

        if(authRequest.getUserName().equals(daemonUserName) && authRequest.getPassword().equals(daemonPassword)) {
            LogResponse logResponse = LogResponse.builder().valid(true).response("Welcome to " + daemonUserName + "Credentials Matched with Vault").build();
            return logResponse;
        } else {
            LogResponse logResponse = LogResponse.builder().valid(false).response("Invalid UserName (OR) Password").build();
            return logResponse;
        }

    }
}
