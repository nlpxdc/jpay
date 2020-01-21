package io.cjf.jpaywepaynotify;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
public class JpayWepayNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpayWepayNotifyApplication.class, args);
        Security.addProvider(new BouncyCastleProvider());
    }

}
