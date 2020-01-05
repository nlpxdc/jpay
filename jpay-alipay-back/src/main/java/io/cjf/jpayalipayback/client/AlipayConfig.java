package io.cjf.jpayalipayback.client;

import com.alipay.api.CertAlipayRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlipayConfig extends CertAlipayRequest {

    public AlipayConfig(@Value("${}") String serverUrl,
                        @Value("${}") String appId,
                        @Value("${}") String privateKey,
                        @Value("${}") String alipayPublicCertPath){
        this.setServerUrl(serverUrl);
        this.setAppId(appId);
        this.setPrivateKey(privateKey);
        this.setFormat("json");
        this.setCharset("utf-8");
        this.setSignType("RSA2");
        this.setAlipayPublicCertPath(alipayPublicCertPath);
    }

}
