package io.cjf.jpayalipayback.client;

import com.alipay.api.CertAlipayRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlipayPubConfig extends CertAlipayRequest {

    public AlipayPubConfig(@Value("${alipay.serverUrl}") String serverUrl,
                           @Value("${alipay.appId}") String appId,
                           @Value("${alipay.privateKey}") String privateKey,
                           @Value("${alipay.certPath}") String certPath,
                           @Value("${alipay.alipayPublicCertPath}") String alipayPublicCertPath,
                           @Value("${alipay.rootCertPath}") String rootCertPath){
        this.setServerUrl(serverUrl);
        this.setAppId(appId);
        this.setPrivateKey(privateKey);
        this.setFormat("json");
        this.setCharset("utf-8");
        this.setSignType("RSA2");
        this.setCertPath(certPath);
        this.setAlipayPublicCertPath(alipayPublicCertPath);
        this.setRootCertPath(rootCertPath);
    }

}
