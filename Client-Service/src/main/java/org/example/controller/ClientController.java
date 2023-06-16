package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.Repository.JwtRepository;
import org.example.model.OrderDetails;
import org.example.model.UserDetails;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    private GetUserVaultCredentials getUserVaultCredentialsService;
    @Autowired
    private  StoreCredentialsService storeCredentialsService;
    @Autowired
    private ValidateUserService validateUserService;
    @Autowired
    private GenerateTokenService generateTokenService;
    @Autowired
    private CreateOrderService createOrderService;
    @Autowired
    private GetOrderDetailsService getOrderDetailsService;

    @Autowired
    private JwtRepository jwtRepository;

    public String username;


    @GetMapping("/welcome")
    public String message(){

        return "Hello world!!! Welcome...";
    }

    @GetMapping("/getJwtToken")
    public String getJwtToken() throws JsonProcessingException {

        generateTokenService.userToken(username).toString();
        return jwtRepository.getJWTToken();
    }


    //Store Credentials to Vault.
    @PostMapping("/storeCredentialsToVault")
    public ResponseEntity<String> registerUser(@RequestBody UserDetails data) throws Exception {

        String jwtToken = jwtRepository.getJWTToken();

        if (jwtToken == null) {

            ResponseEntity.ok(generateTokenService.userToken(username).toString());
        }
        Date tokenExpiryTime = jwtRepository.getTokenExpiryTime();

        Date presentTime = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        dateFormat.format(presentTime);

        try {
            if (tokenExpiryTime != null) {
                Date date1 = dateFormat.parse(String.valueOf(tokenExpiryTime));
                Date date2 = dateFormat.parse(String.valueOf(presentTime));

                if (date1.before(date2) || date1.equals(date2)) {
                    System.out.println("JWT Token Valid");
                    return ResponseEntity.ok(storeCredentialsService.registerUserInVault(data).toString());
                } else {
                    System.out.println("********** Token Expired **********\n\n Please Generate New JWT Token...");
                  //  ResponseEntity.ok(generateTokenService.userToken(username).toString());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/getVaultCredentials")
    public ResponseEntity<String> getVaultCredentials() throws Exception {

        String jwtToken= jwtRepository.getJWTToken();

        System.out.println("JWTToken before generating :"+jwtToken);

        if(jwtToken == null) {

            ResponseEntity.ok(generateTokenService.userToken(username).toString());
        }
        Date tokenExpiryTime = jwtRepository.getTokenExpiryTime();

        Date presentTime = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        dateFormat.format(presentTime);

        try {
            if (tokenExpiryTime != null) {
                Date date1 = dateFormat.parse(String.valueOf(tokenExpiryTime));
                Date date2 = dateFormat.parse(String.valueOf(presentTime));

                if (date1.before(date2) || date1.equals(date2)) {
                    System.out.println("JWT Token Valid");
                    return ResponseEntity.ok(getUserVaultCredentialsService.getUserDetailsFromVault().toString());
                } else {
                    System.out.println("********** Token Expired **********\n\n Please Generate New JWT Token...");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


       // Check for Valid User or not
        @PostMapping("/validateUser")
        public ResponseEntity<String> validateUser(@RequestBody UserDetails data) throws Exception {

            String jwtToken= jwtRepository.getJWTToken();

            System.out.println("JWTToken before generating :"+jwtToken);


            if(jwtToken == null) {

                ResponseEntity.ok(generateTokenService.userToken(username).toString());

            }

            Date tokenExpiryTime = jwtRepository.getTokenExpiryTime();

            Date presentTime = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
            dateFormat.format(presentTime);

            try {
                if (tokenExpiryTime != null) {
                    Date date1 = dateFormat.parse(String.valueOf(tokenExpiryTime));
                    Date date2 = dateFormat.parse(String.valueOf(presentTime));

                    if (date1.before(date2) || date1.equals(date2)) {
                        System.out.println("JWT Token Valid");
                        return ResponseEntity.ok(validateUserService.validateVaultUser(data).toString());
                    } else {
                        System.out.println("********** Token Expired **********\n\n Please Generate New JWT Token...");
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
    }


    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderDetails orderData) throws Exception {

        String jwtToken= jwtRepository.getJWTToken();

        System.out.println("JWTToken before generating :"+jwtToken);


        if(jwtToken == null) {

            ResponseEntity.ok(generateTokenService.userToken(username).toString());
        }
        Date tokenExpiryTime = jwtRepository.getTokenExpiryTime();

        Date presentTime = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        dateFormat.format(presentTime);

        try {
            if (tokenExpiryTime != null) {
                Date date1 = dateFormat.parse(String.valueOf(tokenExpiryTime));
                Date date2 = dateFormat.parse(String.valueOf(presentTime));

                if (date1.before(date2) || date1.equals(date2)) {
                    System.out.println("JWT Token Valid");
                    return ResponseEntity.ok(createOrderService.createNewOrder(orderData).toString());
                } else {
                    System.out.println("********** Token Expired **********\n\n Please Generate New JWT Token...");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getOrderDetails")
    public Optional<String> getOrderDetails() {

        return Optional.ofNullable(getOrderDetailsService.getOrderDetails().toString());
    }


    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable int orderId){

        return ResponseEntity.ok(getOrderDetailsService.getOrderById(orderId));
    }
}




