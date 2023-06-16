# Simple Backend eCommerce Application with SpringBoot, Spring Security and MongoDB as database.

This example app shows a simple eCommerce Application. Developed RestFul APIs using Spring Boot, JPA to interact with database, JWT Authentication for Client validation on Server side, and used Hashicorp Vault to store user Credentials.

**Tools:** For this project it was necessary to use tools such as those shown below.

- **Language:** Java
- **Framework backend:** SpringBoot, Spring Security with Maven
- **CRUD operations:** PostMan
- **Vault:** Hashicorp Vault
**Objective:** Develop an application to register user and store credentials in vault, place Order and get Order details applying micro service patterns implementing JWT Authentication.

**Prerequisites:** Java 11, PostMan, Hashicorp Vault and MongoDB.

- [Getting Started](#getting-started)
- [Modules Description](#modules-description)
- [Links](#links)

## Getting Started

To install this application, run the following commands:

```bash
git clone https://github.com/NiteshReddi/Miracle_ECommerce_POC.git 
cd Miracle_ECommerce_POC
```

This will get a copy of the project installed locally. To configure all of its dependencies and start each app, follow the instructions below.

### Configure Database

Now you can run the server from cmd with file path commad: mongod which runs mongoDB server. 

Once MongoDB compass is installed you must create a document. You must configure in the file `application.configure` located in the path `src/main/resources/`.

In the file `application.configure` you must edit the parameters `spring.data.mongodb.host=localhost` and `spring.data.mongodb.port=27017` and `spring.data.mongodb.database=<MyDocumentName>` with the values you defined.

### Configure Vault:
Run command in cmd. Command: `vault server --dev --dev-root-token-id="<MyVaultToken>"`

This command starts vault server and generate a Root Token:"<MyVaultToken>" & vault Api Address: http://127.0.0.1:8200 by default.
You must configure in the Vault-Service module in file `application.properties` located in the path `src/main/resources/`. 

## Module Description

1)Client-Service: This module we have multiple Api's to interact with all the other services to create Order, Get order Details, Store user Credentials in vault and retrieve user Credentials from vault.

2)JWTToken-Service: This module is used to validate client user on server side. This module has two services, one to generate JWT Token for the client user and other to validate JWT token when accessing other service modules.

3)Vault-Service: This module is used to Store user credentials in Hashicorp vault and retrieve them when required. 

4)Create-Order-Api-Service: This module is used to place an order with the appropriate order details. Once the Api is called, order is placed and order details are stored in DB.

5)Get-Order-Api-Service: This module is used to GET orderDetails. Once the Api is called, order details are retrieved from DB.

## Links

This example uses the following open source libraries:

- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostMan](https://www.postman.com)
