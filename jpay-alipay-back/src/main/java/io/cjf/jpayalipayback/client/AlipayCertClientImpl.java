package io.cjf.jpayalipayback.client;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.stereotype.Service;

@Service
public class AlipayCertClientImpl extends DefaultAlipayClient {

    public AlipayCertClientImpl(AlipayPubConfig alipayPubConfig) throws AlipayApiException {
        super(alipayPubConfig);
    }

}
