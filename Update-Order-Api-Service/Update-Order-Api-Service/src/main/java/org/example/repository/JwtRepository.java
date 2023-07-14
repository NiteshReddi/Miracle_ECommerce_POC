package org.example.repository;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Repository
public class JwtRepository {

    private Date tokenExpiryTime;

    private String JWTToken;

}
