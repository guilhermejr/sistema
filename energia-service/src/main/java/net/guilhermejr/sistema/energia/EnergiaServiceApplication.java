package net.guilhermejr.sistema.energia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EnergiaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergiaServiceApplication.class, args);
    }

}
