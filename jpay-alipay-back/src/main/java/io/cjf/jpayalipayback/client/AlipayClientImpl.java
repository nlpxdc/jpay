package io.cjf.jpayalipayback.client;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.stereotype.Service;

@Service
public class AlipayClientImpl extends DefaultAlipayClient {

    public AlipayClientImpl(AlipayConfig alipayConfig) throws AlipayApiException {
        super(alipayConfig);
    }

}
