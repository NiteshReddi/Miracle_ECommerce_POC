package org.example.repository;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class DaemonRepository {

        private String userName;

        private String password;

}

