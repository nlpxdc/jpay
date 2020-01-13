package io.cjf.jpaywepayback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JpayWepayBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpayWepayBackApplication.class, args);
    }

}
