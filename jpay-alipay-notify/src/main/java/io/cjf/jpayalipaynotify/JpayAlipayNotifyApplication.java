package io.cjf.jpayalipaynotify;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
public class JpayAlipayNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpayAlipayNotifyApplication.class, args);
        Security.addProvider(new BouncyCastleProvider());
    }

}
