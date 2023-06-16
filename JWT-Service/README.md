# Spring Boot JWT Authentication example with Spring Security, Spring Data JPA & JavaKeyStore. 

This example shows implementation of JWT in Spring-Security. Developed RestFul APIs using Spring Boot, Generated JavaKeyStore(jks) for Secure server authentication.

**Tools:** For this project it was necessary to use tools such as those shown below.

- **Language:** Java
- **Framework backend:** SpringBoot with Maven
- **CRUD operations:** PostMan

**Objective:** Generate a JWT token for authorized user and calling GET API with JWT Token.

**Prerequisites:** [Java 11](https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_windows-x64_bin.zip),[PostMan](https://dl.pstmn.io/download/latest/win64)
- [Getting Started](#getting-started)
- [Links](#links)

## Getting Started

To install this application, run the following commands:

```bash
git clone https://github.com/NiteshReddi/JWT-Spring-Security.git
cd JWT-Spring-Security
```

This will get a copy of the project installed locally. To configure all of its dependencies and start each app, follow the instructions below.

Use the Following command as shown to generate the your own keystore.jks file

```bash
keytool -genkeypair -alias keystore -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 3650
```

You must configure in the file `application.properties` located in the path `src/main/resources/`.

In the file `application.properties` you must edit the parameters `spring.h2.console.enabled=true` , `server.ssl.key-store=./keystore.jks` , `server.ssl.key-store-type=pkcs12` , `server.ssl.key-store-password=********` , `server.ssl.key-password=********` , `server.ssl.key-alias=keystore` and `server.ssl.enabled=true` with the values you defined.


To run the application, open command link and run "mvn spring-boot:run" or alternatively you can run the program in the IDE of your choosing.

## Links

This example uses the following open source libraries:

- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostMan](https://www.postman.com)
