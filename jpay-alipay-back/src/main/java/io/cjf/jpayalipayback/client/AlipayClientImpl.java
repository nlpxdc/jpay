package io.cjf.jpayalipayback.client;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlipayClientImpl extends DefaultAlipayClient {

//    public AlipayClientImpl(@Value("${alipay.serverUrl}") String serverUrl,
//                            @Value("${alipay.appId}") String appId,
//                            @Value("${alipay.privateKey}") String privateKey,
//                            @Value("${alipay.alipayPublicKey}") String alipayPublicKey) {
//        super(serverUrl, appId, privateKey, "json", "utf-8", alipayPublicKey, "RSA2");
//    }

    public AlipayClientImpl(AlipayPubConfig alipayPubConfig) throws AlipayApiException {
        super(alipayPubConfig);
    }

}
