package io.cjf.jpaywepayback.client;

import feign.Client;
import io.cjf.jpaywepayback.dto.PayDownloadFundflowDTO;
import io.cjf.jpaywepayback.dto.PayRefundDTO;
import io.cjf.jpaywepayback.dto.PayReverseDTO;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.security.*;

@FeignClient(name = "WepayCertApi", url = "${wepay.url}", configuration = WepayCertApi.Configuration.class)
public interface WepayCertApi {

    @PostMapping(value = "/secapi/pay/refund", consumes = MediaType.APPLICATION_XML_VALUE)
    String payRefund(@RequestBody PayRefundDTO payRefundDTO);

    @PostMapping(value = "/pay/downloadfundflow", consumes = MediaType.APPLICATION_XML_VALUE)
    String payDownloadFundflow(@RequestBody PayDownloadFundflowDTO payDownloadFundflowDTO);

    @PostMapping(value = "/secapi/pay/reverse", consumes = MediaType.APPLICATION_XML_VALUE)
    String payReverse(@RequestBody PayReverseDTO payReverseDTO);

    class Configuration {

        @Value("${wepay.cert.filepath}")
        private String filepath;

        @Value("${wepay.cert.password}")
        private String password;

        @Autowired
        private SecureRandom secureRandom;

        @Bean
        Client client() throws Exception {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            final char[] pwdArray = password.toCharArray();
            try(FileInputStream inputStream = new FileInputStream(filepath)) {
                keyStore.load(inputStream, pwdArray);
            }
            final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, pwdArray);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, secureRandom);

            SSLSocketFactory socketFactory = sslContext.getSocketFactory();

            Client client = new Client.Default(socketFactory, new NoopHostnameVerifier());
            return client;
        }


    }
}
