package org.example;


import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class MainApp {
    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User("Nitesh", "password"),
                new User("user1", "pwd1")
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }

    public static void main(String[] args) {

        SpringApplication.run(MainApp.class, args);
    }

}
