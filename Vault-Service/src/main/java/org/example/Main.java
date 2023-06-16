package org.example;

import org.example.repository.DaemonRepository;
import org.example.service.GetVaultCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class Main {

    private final DaemonRepository daemonRepository;

    private final GetVaultCredentialsService getVaultCredentialsService;


    @Autowired
    public Main(DaemonRepository daemonRepository, GetVaultCredentialsService getVaultCredentialsService) {
        this.daemonRepository = daemonRepository;
        this.getVaultCredentialsService = getVaultCredentialsService;
    }

    @PostConstruct
    public void startDaemonThread() {
        Runnable daemonRunner = () -> {
            while (true) {
                System.out.println("****************\n I'm a Daemon.");

                try {
                    getVaultCredentialsService.getVaultCredentials();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    Thread.sleep(500000); // 5 minutes
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread daemonThread = new Thread(daemonRunner);
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class);
    }
}